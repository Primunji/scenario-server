package dev.euns.fishingstopserver.domain.scenario.service

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.AssistantRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import dev.euns.fishingstopserver.domain.scenario.dto.reqeust.CreateScenarioRequest
import dev.euns.fishingstopserver.domain.scenario.dto.response.CreateScenarioResponse
import dev.euns.fishingstopserver.domain.scenario.entity.Scenario
import dev.euns.fishingstopserver.domain.scenario.repository.ScenarioRepository
import dev.euns.fishingstopserver.infra.openai.PromptGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ScenarioService(
    private val scenarioRepository: ScenarioRepository,
    private val promptGenerator: PromptGenerator
) {
    @Transactional
    suspend fun createScenario(response: CreateScenarioRequest): ResponseEntity<CreateScenarioResponse> {
        val result = promptGenerator.generate(response.name, response.content, response.prompt)
//
//        val scenario = Scenario(
//            name = response.name,
//            content = response.content,
//            profile_url = "",
//            prompt = ""
//        )
//
//        withContext(Dispatchers.IO) {
//            scenarioRepository.save(scenario)
//        }

        return ResponseEntity(CreateScenarioResponse(success = true, reason = result), HttpStatus.OK)
    }
}