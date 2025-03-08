package dev.euns.scenarioserver.domain.call.service

import com.aallam.openai.api.thread.ThreadId
import dev.euns.scenarioserver.domain.call.entity.Call
import dev.euns.scenarioserver.domain.call.repository.CallRepository
import dev.euns.scenarioserver.domain.scenario.repository.ScenarioRepository
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import dev.euns.scenarioserver.global.utils.OpenAiUtils
import kotlinx.coroutines.Dispatchers
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
    private val userRepository: UserRepository
) {
    @Transactional
    suspend fun createCall(principal: Principal, scenario_id: Long): BaseResponse<Any> {
        val thread_id = openAiUtils.createThread()

        val scenario = withContext(Dispatchers.IO) {
            scenarioRepository.findById(scenario_id).also { println(it) }.orElseThrow {
                IllegalArgumentException("해당 시나리오 ID가 존재하지 않습니다. ID: ${scenario_id}")
            }
        }

        val user = withContext(Dispatchers.IO) {
            userRepository.findByUsername(principal.name).also { println(it) }.orElseThrow {
                IllegalArgumentException("해당 아이디가 존재하지 않습니다. ${principal.name}")
            }
        }

        return withContext(Dispatchers.IO) {
            val call = Call(
                user_id = user.id,
                scenario_id = scenario_id,
                thread_id = thread_id,
                assistant_id = scenario.assistant_id,
            )

            callRepository.save(call)

            BaseResponse(status = 200, message = "성공적으로 전화를 생성 하였습니다.")
        }

    }

}