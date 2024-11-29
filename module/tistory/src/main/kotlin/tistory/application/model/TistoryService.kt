package tistory.application.model

import insta.application.model.InstaService
import insta.domain.InstaPost
import tistory.adapter.out.db.TistoryRepository
import tistory.adapter.out.db.TistoryUploadFailLogRepository
import tistory.application.dto.TistoryRequestDTO
import tistory.application.model.selenium.tistory.TistoryNewPostPage
import tistory.domain.TistoryPost
import tistory.domain.TistoryUploadFailLog
import tistory.global.exception.TistoryUploadException
import tistory.global.type.UploadStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import java.time.OffsetDateTime
import org.springframework.stereotype.Service

@Service
class TistoryService(
    private val instaService: InstaService,
    private val tistoryNewPostPage: TistoryNewPostPage,
    private val tistoryRepository: TistoryRepository,
    private val tistoryUploadFailLogRepository: TistoryUploadFailLogRepository,
) {
    @Transactional
    fun fetchTistoryPosts(): List<TistoryRequestDTO> {
        val instaPosts = instaService.getFetchedInstaPosts()
        if (instaPosts.isEmpty()) return emptyList()

        val tistoryPosts = instaPosts.mapNotNull {
            convertToTistoryPost(it)
        }

        instaPosts.forEach {
            it.tistoryFetched = true
        }

        val insertedTistoryPosts = tistoryRepository.saveAll(tistoryPosts)
        instaService.saveInstaPosts(instaPosts)

        return insertedTistoryPosts.map {
            TistoryRequestDTO(it)
        }
    }

    @Transactional
    fun uploadTistoryPosts(): List<String> {
        val tistoryPosts =
            tistoryRepository.findFirstByUploadStatusNotOrderByFetchedTimestamp(UploadStatus.UPLOADED)

        val okUrls = mutableListOf<String>()

        if (tistoryPosts.isEmpty()) return okUrls

        tistoryPosts.forEach {
            try {
                logger.info { "게시글을 업로드합니다.: ${it.title}" }

                val url = uploadTistoryPost(
                    TistoryRequestDTO(it),
                )

                it.uploadTimestamp = OffsetDateTime.now()
                it.tistoryUrl = url
                it.uploadStatus = UploadStatus.UPLOADED
                okUrls.add(url)
            } catch (e: TistoryUploadException) {
                logger.error(e) { e.message }
                it.uploadStatus = UploadStatus.FAILED
                saveFailLog(it, e)
            }

            tistoryRepository.save(it)
            logger.info { "새 게시글: ${it.tistoryUrl}" }
        }

        logger.info { "${okUrls.size}개의 게시글을 업로드 했습니다." }

        return okUrls
    }

    fun uploadTistoryPost(tistoryRequestDTO: TistoryRequestDTO): String =
        tistoryRequestDTO.let { dto ->
            with(tistoryNewPostPage) {
                setUp()
                handlePopup()
                setCategory(dto.category)
                setHTML()
                setTitle(dto.title)
                setContent(dto.content)
                setTags(dto.tags)
                submit() // 가장 최신 글 Url
            }
        }

    fun convertToTistoryPost(instaPost: InstaPost): TistoryPost? {
        try {
            val tistoryPost = parseInstaPost(instaPost)
            tistoryPost.apply {
                content = toHtmlContent(content, instaPost.mediaUrl)
            }
            return tistoryPost
        } catch (e: Exception) {
            logger.error(e) { "인스타 -> 티스토리 변환 실패" }
            return null
        }
    }

    fun parseInstaPost(instaPost: InstaPost): TistoryPost {
        val groupValues = captionRegex.find(instaPost.caption)!!.groupValues
        val category = groupValues[1]
        val title = groupValues[2]
        val content = groupValues[3]
        val tags = groupValues[4].split(" ").map { it.trim().drop(1) }

        return TistoryPost(
            instaId = instaPost.instaId,
            category = category,
            title = title,
            content = content,
            tags = tags.joinToString("|"),
            uploadStatus = UploadStatus.FETCHED,
            fetchedTimestamp = OffsetDateTime.now(),
        )
    }

    fun toHtmlContent(content: String, mediaUrl: String): String {
        var htmlContent = "<img src=\"$mediaUrl\" width=\"700\"/><br/><br/>"

        contentRegex.findAll(content).forEach {
            htmlContent += "<p>" + it.groupValues[1] + "</p>"
        }

        return htmlContent
    }

    fun saveFailLog(tistoryPost: TistoryPost, exception: TistoryUploadException) {
        val failLog = TistoryUploadFailLog(
            tistoryId = tistoryPost.id,
            reason = "${exception.message} ${exception.stackTraceToString()}",
            retryTimestamp = OffsetDateTime.now(),
        )

        tistoryUploadFailLogRepository.save(failLog)
    }

    companion object {
        /**
         * \[diary]
         * <this was a fantastic day>
         * hello I met kanye west and that was amazing.
         * I hope you like kanye.
         * #kanye #west #awsome
         */
        val captionRegex = Regex("\\[(.*)][\\s\\S]*<(.*)>\\s*([^#]*)\\s*((#[^#]*)*)")
        val contentRegex = Regex("(.*)")

        val logger = KotlinLogging.logger { }
    }
}
