package dev.euns.scenarioserver.domain.call.service

import com.aallam.openai.api.thread.ThreadId
import dev.euns.scenarioserver.domain.call.dto.response.CreateCallResponse
import dev.euns.scenarioserver.domain.call.entity.Call
import dev.euns.scenarioserver.domain.call.repository.CallRepository
import dev.euns.scenarioserver.domain.contacts.entity.Contacts
import dev.euns.scenarioserver.domain.contacts.repository.ContactsRepository
import dev.euns.scenarioserver.domain.scenario.repository.ScenarioRepository
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import dev.euns.scenarioserver.global.utils.OpenAiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal
import java.util.*

@Service
class CallService(
    private val openAiUtils: OpenAiUtils,
    private val callRepository: CallRepository,
    private val scenarioRepository: ScenarioRepository,
    private val contactsRepository: ContactsRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createCall(principal: Principal, scenario_id: Long): BaseResponse<CreateCallResponse> {
        val thread_id = runBlocking{openAiUtils.createThread()}

        val scenario =
            scenarioRepository.findById(scenario_id).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 시나리오 ID가 존재하지 않습니다. ID: ${scenario_id}")
            }


        val user =
            userRepository.findByUsername(principal.name).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 아이디가 존재하지 않습니다. ${principal.name}")
        }

        val call = Call(
            user_id = user.id,
            scenario_id = scenario_id,
            thread_id = thread_id,
            assistant_id = scenario.assistant_id,
        )

        callRepository.save(call)

        val contacts = Contacts(
            scenario_id = scenario_id,
            userId = user.id,
            name = scenario.name,
            content = scenario.content,
            profile_url = scenario.profile_url,
        )

        contactsRepository.save(contacts)

        val response = CreateCallResponse(
            thread_id=thread_id
        )
        return BaseResponse(status = 200, message = "성공적으로 전화를 생성 하였습니다.", data=response)
    }

}