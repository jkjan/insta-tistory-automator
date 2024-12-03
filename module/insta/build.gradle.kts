import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    named<BootJar>("bootJar") {
        enabled = false
    }
}

dependencies {
    implementation(project(":module:common"))
}
