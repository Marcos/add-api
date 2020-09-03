package com.add.addapi.dnd5.mainclass

import com.add.addapi.dnd5.api.ApiCache.apiCache
import com.add.addapi.dnd5.api.DND5_API_URL
import com.add.addapi.dnd5.api.MainClass
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate


@Repository
class MainClassRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndex(index: String): MainClass {
        return apiCache.get(index) {
            val response = restTemplate.getForEntity("$DND5_API_URL/classes/$index", MainClass::class.java)
            when {
                response.statusCode.is2xxSuccessful -> response.body
                else -> throw InvalidMainClass()
            }
        } as MainClass
    }
}