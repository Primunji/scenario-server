package dev.euns.scenarioserver.domain.recent.repository

import dev.euns.scenarioserver.domain.recent.entity.Recent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RecentRepository : JpaRepository<Recent, Long> {
    fun findAllByUserId(userId: Long): List<Recent>
}