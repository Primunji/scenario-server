package dev.euns.fishingstopserver.domain.assistant.service

import com.aallam.openai.client.OpenAI
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.message.MessageContent
import com.aallam.openai.api.assistant.AssistantRequest
import com.aallam.openai.api.core.Role
import com.aallam.openai.api.core.Status
import com.aallam.openai.api.message.MessageRequest
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.api.run.RunRequest
import kotlinx.coroutines.delay
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Service

@Service
class AssistantService(
    @Value("\${openai.api-key}")
    private val apiKey: String
) {
    @OptIn(BetaOpenAI::class)
    suspend fun getAssistant(): String {
        val openai:OpenAI = OpenAI(apiKey)
        val assistant = openai.assistant(
            request = AssistantRequest(
                name = "Human Rights Bot",
                instructions = "한국어로 상냥하게 말해봐",
                model = ModelId("gpt-4o-mini"),
            )
        )

        val thread = openai.thread()
        openai.message(
            threadId = thread.id, request = MessageRequest(
                role = Role.User,
                content = "어떤걸 하면 좋을까??"
            )
        )

        val run = openai.createRun(
            thread.id, request = RunRequest(
                assistantId = assistant.id,
                instructions = "어떤걸 하면 좋을지 한줄 내로 간단하게 설명해주세요",
            )
        )

        do {
            delay(1500)
            val retrievedRun = openai.getRun(threadId = thread.id, runId = run.id)
        } while (retrievedRun.status != Status.Completed)
//
//        val runSteps = openai.runSteps(threadId = run.threadId, runId = run.id)
//        println("\nRun steps: ${runSteps.size}")

        val assistantMessages = openai.messages(thread.id)
        for (message in assistantMessages) {
            val textContent = message.content.first() as? MessageContent.Text ?: error("Expected MessageContent.Text")
            println(textContent.text.value)
        }

        return "123"
    }
}