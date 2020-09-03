package com.add.addapi.dnd5.subclass

import com.add.addapi.dnd5.api.ApiCache.apiCache
import com.add.addapi.dnd5.api.DND5_API_URL
import com.add.addapi.dnd5.api.SubClass
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate


@Repository
class SubClassRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndex(index: String): SubClass {
        return apiCache.get(index) {
            val response = restTemplate.getForEntity("$DND5_API_URL/subclasses/$index", SubClass::class.java)
            when {
                response.statusCode.is2xxSuccessful -> response.body
                else -> throw InvalidSubClass()
            }
        } as SubClass
    }
}