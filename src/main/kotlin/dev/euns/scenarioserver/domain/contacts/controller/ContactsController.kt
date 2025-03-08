package dev.euns.scenarioserver.domain.contacts.controller

import dev.euns.scenarioserver.domain.contacts.dto.request.CreateContactDto
import dev.euns.scenarioserver.domain.contacts.entity.Contacts
import dev.euns.scenarioserver.domain.contacts.service.ContactsService
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/contacts")
class ContactsController(
    private val contactsService: ContactsService
) {
    @PostMapping
    fun createContact(
        principal: Principal,
        @RequestBody request: CreateContactDto
    ): BaseResponse<Unit> {
        return contactsService.createContact(principal, request)
    }

    @GetMapping
    fun getContacts(
        principal: Principal
    ): BaseResponse<List<Contacts>> {
        return contactsService.getContacts(principal)
    }
}