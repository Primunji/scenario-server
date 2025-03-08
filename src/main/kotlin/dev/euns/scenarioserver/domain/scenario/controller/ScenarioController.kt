package dev.euns.scenarioserver.domain.scenario.controller

import dev.euns.scenarioserver.domain.scenario.dto.reqeust.CreateScenarioRequest
import dev.euns.scenarioserver.domain.scenario.dto.response.CreateScenarioResponse
import dev.euns.scenarioserver.domain.scenario.entity.Scenario
import dev.euns.scenarioserver.domain.scenario.repository.ScenarioRepository
import dev.euns.scenarioserver.domain.scenario.service.ScenarioService
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/scenario")
class ScenarioController(
    private val scenarioService: ScenarioService,
) {
    @PostMapping
    fun createScenario(@RequestBody request: CreateScenarioRequest): BaseResponse<Unit> {
        return scenarioService.createScenario(request)
    }
    @GetMapping
    fun getScenario(): BaseResponse<Any> {
        return scenarioService.getScenario()
    }
}

