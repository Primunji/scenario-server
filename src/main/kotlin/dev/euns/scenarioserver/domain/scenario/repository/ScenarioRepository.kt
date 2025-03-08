package dev.euns.scenarioserver.domain.scenario.repository

import dev.euns.scenarioserver.domain.scenario.entity.Scenario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ScenarioRepository : CrudRepository<Scenario, Long> {
}