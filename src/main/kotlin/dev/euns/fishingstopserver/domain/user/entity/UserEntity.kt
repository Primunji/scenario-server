package dev.euns.fishingstopserver.domain.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var password: String,
)