package dev.euns.scenarioserver.domain.recent.service

import dev.euns.scenarioserver.domain.recent.entity.Recent
import dev.euns.scenarioserver.domain.recent.repository.RecentRepository
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class RecentService(
    private val recentRepository: RecentRepository,
    private val userRepository: UserRepository

) {
    fun getRecentCall(principal: Principal): BaseResponse<List<Recent>> {
        val user =
            userRepository.findByUsername(principal.name).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 아이디가 존재하지 않습니다. ${principal.name}")
            }

        val recent = recentRepository.findAllByUserId(user.id)

        return BaseResponse(status = 200, message = "최근 연락을 성공적으로 로드하였습니다.", data=recent)
    }
}