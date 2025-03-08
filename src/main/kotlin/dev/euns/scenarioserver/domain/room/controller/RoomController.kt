package dev.euns.scenarioserver.domain.room.controller

import com.fasterxml.jackson.databind.ser.Serializers.Base
import dev.euns.scenarioserver.domain.room.entity.Room
import dev.euns.scenarioserver.domain.room.repository.RoomRepository
import dev.euns.scenarioserver.domain.room.service.RoomService
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/room")
class RoomController(
    private val roomService: RoomService

) {
    @GetMapping
    fun getChatRooms(principal: Principal): BaseResponse<List<Room>> {
        return roomService.getRoom(principal)
    }
}