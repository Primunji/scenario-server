package dev.euns.scenarioserver.domain.contacts.service

import dev.euns.scenarioserver.domain.contacts.dto.request.CreateContactDto
import dev.euns.scenarioserver.domain.contacts.entity.Contacts
import dev.euns.scenarioserver.domain.contacts.repository.ContactsRepository
import dev.euns.scenarioserver.domain.scenario.repository.ScenarioRepository
import dev.euns.scenarioserver.domain.user.repository.UserRepository
import dev.euns.scenarioserver.global.dto.BaseResponse
import io.swagger.v3.oas.models.info.Contact
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.security.Principal

@Service
class ContactsService(
    private val contactsRepository: ContactsRepository,
    private val scenarioRepository: ScenarioRepository,
    private val userRepository: UserRepository
) {
    fun createContact(
        principal: Principal,
        request: CreateContactDto
    ): BaseResponse<Unit>  {
        val scenario =
            scenarioRepository.findById(request.scenario_id).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 시나리오 ID가 존재하지 않습니다. ID: ${request.scenario_id}")
            }

        val user =
            userRepository.findByUsername(principal.name).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 아이디가 존재하지 않습니다. ${principal.name}")
            }

        val contacts = Contacts(
            scenario_id = request.scenario_id,
            name = scenario.name,
            content = scenario.content,
            profile_url = scenario.profile_url,
            userId = user.id
        )

        contactsRepository.save(contacts)

        return BaseResponse(status = 200, message = "성공적으로 연락처가 생성 되었습니다.")
    }

    fun getContacts(
        principal: Principal,
    ): BaseResponse<List<Contacts>> {
        val user =
            userRepository.findByUsername(principal.name).also { println(it) }.orElseThrow {
                throw IllegalArgumentException("해당 아이디가 존재하지 않습니다. ${principal.name}")
            }

        val contacts = contactsRepository.findAllByUserId(user.id)

        return BaseResponse(status = 200, message = "성공적으로 연락처를 불러왔습니다.", data = contacts)
    }

}