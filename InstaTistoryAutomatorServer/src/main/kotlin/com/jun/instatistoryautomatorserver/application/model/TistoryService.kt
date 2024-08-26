package com.jun.instatistoryautomatorserver.application.model

import com.jun.instatistoryautomatorserver.InstaTistoryAutomatorServerApplication.Companion.logger
import com.jun.instatistoryautomatorserver.adapter.out.db.InstaRepository
import com.jun.instatistoryautomatorserver.adapter.out.db.TistoryRepository
import com.jun.instatistoryautomatorserver.application.dto.TistoryRequestDTO
import com.jun.instatistoryautomatorserver.application.model.selenium.tistory.NowherelandTistoryManageNPage
import com.jun.instatistoryautomatorserver.domain.InstaPost
import com.jun.instatistoryautomatorserver.domain.TistoryPost
import com.jun.instatistoryautomatorserver.global.exception.TistoryException
import com.jun.instatistoryautomatorserver.global.type.UploadStatus
import jakarta.transaction.Transactional
import java.time.OffsetDateTime
import org.springframework.stereotype.Service

@Service
class TistoryService(
    private val nowherelandTistoryManageNPage: NowherelandTistoryManageNPage,
    private val instaRepository: InstaRepository,
    private val tistoryRepository: TistoryRepository,
) {
    @Transactional
    fun fetchTistoryPost(): List<TistoryRequestDTO> {
        val instaPost = instaRepository.findByTistoryFetched(tistoryFetched = false)
        if (instaPost.isEmpty()) return emptyList()

        val tistoryPost = instaPost.map {
            convertToTistoryPost(it)
        }

        for (i in tistoryPost.indices) {
            tistoryPost[i].apply {
                content = getHtmlContent(content!!, instaPost[i].mediaUrl!!)
            }
        }

        instaPost.forEach {
            it.fetchedTimestamp = OffsetDateTime.now()
            it.tistoryFetched = true
        }

        val insertedTistoryPost = tistoryRepository.saveAll(tistoryPost)
        return insertedTistoryPost.map {
            TistoryRequestDTO(it)
        }
    }

    @Transactional
    fun uploadTistoryPosts() {
        val tistoryPost = tistoryRepository.findByUploadStatusNot(UploadStatus.UPLOADED)
        if (tistoryPost.isEmpty()) return

        tistoryPost.forEach {
            try {
                uploadTistoryPost(
                    TistoryRequestDTO(it),
                )

                it.uploadTimestamp = OffsetDateTime.now()

                if (it.uploadStatus == UploadStatus.FAILED) {
                    // tistory upload fail log.retry_timestamp = now()
                }

                it.uploadStatus = UploadStatus.UPLOADED
            } catch (e: TistoryException) {
                // tistory upload fail log 업로드
                // reason = "${e.message} ${e.stackTrace}"
                // tistory_id = it.id
                logger.error(e) { e.message }
                it.uploadStatus = UploadStatus.FAILED
            }
            tistoryRepository.save(it)
        }
    }

    fun uploadTistoryPost(tistoryRequestDTO: TistoryRequestDTO) {
        tistoryRequestDTO.let { dto ->
            with(nowherelandTistoryManageNPage) {
                setUp()
                handlePopup()
                setCategory(dto.category)
                setHTML()
                setTitle(dto.title)
                setContent(dto.content)
                setTags(dto.tags)
                submit()
                // url 가져와서 반환
            }
        }
    }

    fun convertToTistoryPost(instaPost: InstaPost): TistoryPost {
        val groupValues = regex.find(instaPost.caption!!)!!.groupValues
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

    fun getHtmlContent(content: String, mediaUrl: String): String {
        var htmlContent = "<img src=\"$mediaUrl\"/><p></p>"

        contentRegex.findAll(content).forEach {
            htmlContent += "<p>" + it.groupValues[1] + "</p>"
        }

        return htmlContent
    }

    companion object {
        /**
         * \[diary]
         * <this was a fantastic day>
         * hello I met kanye west and that was amazing.
         * I hope you like kanye.
         * #kanye #west #awsome
         */
        val regex = Regex("\\[(.*)][\\s\\S]*<(.*)>\\s*([^#]*)\\s*((#[^#]*)*)")
        val contentRegex = Regex("(.*)")
    }
}
