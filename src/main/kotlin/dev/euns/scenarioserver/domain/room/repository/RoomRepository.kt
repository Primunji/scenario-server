package dev.euns.scenarioserver.domain.room.repository

import dev.euns.scenarioserver.domain.recent.entity.Recent
import dev.euns.scenarioserver.domain.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RoomRepository : JpaRepository<Room, Long> {
    fun findAllByUserId(userId: Long): List<Room>
}