package dev.euns.scenarioserver.domain.scenario.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateScenarioResponse (
    val success : Boolean,
    val reason : String? = null,
)