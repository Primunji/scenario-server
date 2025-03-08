package dev.euns.scenarioserver.domain.room.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "room")
data class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val thread_id: String,

    val scenario_id: Long,

    @Column(name = "user_id")
    val userId: Long,

    val name: String,

    val content: String,

    val profile_url: String,

    val recent_message: String,

    val last_message: Date,

    val created_at: LocalDateTime = LocalDateTime.now(),
)