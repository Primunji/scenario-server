package dev.euns.scenarioserver.domain.call.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "calls")
data class Call(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val user_id: Long,

    val scenario_id: Long,

    val thread_id: String,

    val assistant_id: String,

    val created_at: LocalDateTime = LocalDateTime.now()
)