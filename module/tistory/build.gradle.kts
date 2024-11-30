import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    named<BootJar>("bootJar") {
        enabled = false
    }
}

dependencies {
    implementation(project(":module:common"))
    implementation(project(":module:insta"))

    // 크롤링
    implementation("org.seleniumhq.selenium:selenium-java")
}
