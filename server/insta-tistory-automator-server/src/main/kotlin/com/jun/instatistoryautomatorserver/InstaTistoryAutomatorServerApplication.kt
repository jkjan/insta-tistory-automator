package com.jun.instatistoryautomatorserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["common", "insta", "tistory"])
class InstaTistoryAutomatorServerApplication

fun main(args: Array<String>) {
    runApplication<InstaTistoryAutomatorServerApplication>(*args)
}
