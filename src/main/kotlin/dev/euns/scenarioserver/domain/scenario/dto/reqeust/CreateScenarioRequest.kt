package dev.euns.scenarioserver.domain.scenario.dto.reqeust

data class CreateScenarioRequest(
    val name: String,
    val content: String,
    val prompt: String,
    val imageUrl: String?
)