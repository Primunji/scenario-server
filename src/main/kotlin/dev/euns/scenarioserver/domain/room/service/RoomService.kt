package dev.euns.scenarioserver.domain.room.service

import dev.euns.scenarioserver.domain.room.entity.Room
import dev.euns.scenarioserver.domain.room.repository.RoomRepository
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class RoomService(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) {
    fun getRoom(principal: Principal) : BaseResponse<List<Room>> {
        val user =
            userRepository.findByUsername(principal.name).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 아이디가 존재하지 않습니다. ${principal.name}")
            }
        val room = roomRepository.findAllByUserId(user.id)

        return BaseResponse(status = 200, message = "채팅방을 조회했습니다.", data=room)
    }
}