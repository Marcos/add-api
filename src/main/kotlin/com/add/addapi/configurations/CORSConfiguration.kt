package com.add.addapi.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.HEAD
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.PATCH
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.HttpMethod.TRACE
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CORSConfiguration : WebMvcConfigurer {

    @Suppress("SpreadOperator")
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
    }
}
