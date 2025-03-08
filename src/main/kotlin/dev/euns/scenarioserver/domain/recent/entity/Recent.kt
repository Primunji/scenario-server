package dev.euns.scenarioserver.domain.recent.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "recent")
data class Recent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val scenario_id: Long,

    @Column(name = "user_id")
    val userId: Long,

    val name: String,

    val content: String,

    val profile_url: String,

    val created_at: LocalDateTime = LocalDateTime.now(),
)