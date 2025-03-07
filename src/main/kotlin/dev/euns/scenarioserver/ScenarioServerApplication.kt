package dev.euns.scenarioserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScenarioServerApplication
fun main(args: Array<String>) {
    runApplication<ScenarioServerApplication>(*args)
}
