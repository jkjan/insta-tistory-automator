import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    named<BootJar>("bootJar") {
        enabled = true
    }

    named<Jar>("jar") {
        enabled = false
    }
}

dependencies {
    implementation(project(":module:common"))
    implementation(project(":module:insta"))
    implementation(project(":module:tistory"))

    // 기타
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}
