package com.add.addapi.configurations

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
            .requestFactory { SimpleClientHttpRequestFactory() }
            .build()
}
