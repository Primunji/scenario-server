package dev.euns.scenarioserver.domain.recent.controller

import dev.euns.scenarioserver.domain.recent.entity.Recent
import dev.euns.scenarioserver.domain.recent.service.RecentService
import dev.euns.scenarioserver.global.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/recent")
class RecentController(
    private val recentService: RecentService
) {

    @GetMapping
    fun getRecentCallData(
        principal: Principal,
    ): BaseResponse<List<Recent>> {
        return recentService.getRecentCall(principal)
    }
}