package dev.euns.scenarioserver.domain.contacts.repository

import dev.euns.scenarioserver.domain.contacts.entity.Contacts
import org.springframework.data.jpa.repository.JpaRepository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ContactsRepository : JpaRepository<Contacts, Long> {
    fun findAllByUserId(userId: Long): List<Contacts>
}