package dev.euns.scenarioserver.domain.scenario.service

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.assistant.AssistantRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.euns.scenarioserver.domain.auth.dto.response.AuthTokenResponse
import dev.euns.scenarioserver.domain.scenario.dto.reqeust.CreateScenarioRequest
import dev.euns.scenarioserver.domain.scenario.dto.response.CreateScenarioResponse
import dev.euns.scenarioserver.domain.scenario.entity.Scenario
import dev.euns.scenarioserver.domain.scenario.repository.ScenarioRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import dev.euns.scenarioserver.infra.openai.PromptGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional(readOnly = true)
class ScenarioService(
    private val scenarioRepository: ScenarioRepository,
    private val promptGenerator: PromptGenerator,

) {
    private val restTemplate = RestTemplate()

    fun getScenario(): BaseResponse<List<Scenario>> {
        val result = scenarioRepository.findAll().toList()
        return BaseResponse(
            message = "시나리오 목록을 성공적으로 불러왔습니다.",
            data = result
        )
    }

    @Transactional
    fun createScenario(response: CreateScenarioRequest): BaseResponse<Unit> {
        val user_content = "${response.name}\n\n${response.content}\n\n${response.prompt}"
        val prompt: HttpPromptResponse = createPrompt(user_content)
        if (prompt.prompt.isBlank()) {
            return BaseResponse(status=500,message="프롬포트가 생성되지 않았습니다.")
        }

        val scenario = Scenario(
            name = response.name,
            content = response.content,
            profile_url = "",
            actor_id = "",
            prompt = response.prompt,
            assistant_id = prompt.assistant_id,
        )
        scenarioRepository.save(scenario)

        return BaseResponse(status=200, message="성공적으로 프롬포트를 생성하였습니다.")
    }

    data class HttpPromptResponse(
        val prompt: String,
        val assistant_id: String
    )

    private fun createPrompt(message: String): HttpPromptResponse {
        val mapper = ObjectMapper()
        val headers = HttpHeaders().apply {
            set("ContentType", "application/json")
        }

        val requestBody = mapOf(
            "message" to message,
            "assistant_id" to "asst_pqLwsGAZj9mieSFzUGXgvAZK",

        )
        val entity = HttpEntity(requestBody, headers)
        val response = restTemplate.exchange("http://localhost:8080/prompt", HttpMethod.POST, entity, String::class.java)
        val rootNode: JsonNode = mapper.readTree(response.body)
        return HttpPromptResponse(prompt =rootNode["prompt"]["user_info"].asText(), assistant_id = rootNode["assistant_id"].asText())
    }
}