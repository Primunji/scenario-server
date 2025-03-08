package dev.euns.scenarioserver.domain.call.repository

import dev.euns.scenarioserver.domain.call.entity.Call
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CallRepository : CrudRepository<Call, Long> {

}