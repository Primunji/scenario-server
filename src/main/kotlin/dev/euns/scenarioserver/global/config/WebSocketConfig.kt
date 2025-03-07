package dev.euns.scenarioserver.global.config

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class WebSocketConfig(private val applicationContext: ApplicationContext) {

    @Bean
    fun handlerMapping(): HandlerMapping {
        val handlers = mutableMapOf<String, WebSocketHandler>()

        applicationContext.getBeansWithAnnotation(Gateway::class.java)
            .forEach { (_, bean) ->
                if (bean is WebSocketHandler) {
                    val path = bean::class.java.getAnnotation(Gateway::class.java).path
                    handlers[path] = bean
                }
            }

        return SimpleUrlHandlerMapping(handlers, 1)
    }

    @Bean
    fun handlerAdapter() = WebSocketHandlerAdapter()
}
