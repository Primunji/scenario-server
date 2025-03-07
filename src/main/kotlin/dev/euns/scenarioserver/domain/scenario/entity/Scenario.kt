package dev.euns.scenarioserver.domain.scenario.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "Scenario")
data class Scenario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val content: String,

    val profile_url: String,

    val prompt: String,

    val created_at: LocalDateTime = LocalDateTime.now(),

    )