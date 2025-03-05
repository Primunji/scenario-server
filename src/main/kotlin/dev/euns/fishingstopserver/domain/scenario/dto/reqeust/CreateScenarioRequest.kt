package dev.euns.fishingstopserver.domain.scenario.dto.reqeust

data class CreateScenarioRequest(
    val name: String,
    val content: String,
    val prompt: String,
)