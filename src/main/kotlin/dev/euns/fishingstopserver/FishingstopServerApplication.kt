package dev.euns.fishingstopserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FishingstopServerApplication

fun main(args: Array<String>) {
    runApplication<FishingstopServerApplication>(*args)
}
