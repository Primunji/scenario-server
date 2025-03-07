package dev.euns.scenarioserver.global.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService (
    private val redisTemplate: StringRedisTemplate
) {
    fun storeRefreshToken(id: Long, refreshToken: String) {
        redisTemplate.opsForValue().set("refreshToken:$id", refreshToken, 1, TimeUnit.DAYS)
    }

    fun getRefreshToken(id: Long): String? {
        return redisTemplate.opsForValue().get("refreshToken:$id")
    }
}