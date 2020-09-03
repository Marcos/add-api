package com.add.addapi.dnd5.race

import com.add.addapi.dnd5.api.ApiCache.apiCache
import com.add.addapi.dnd5.api.DND5_API_URL
import com.add.addapi.dnd5.api.Race
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate


@Repository
class RaceRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndex(index: String): Race {
        return apiCache.get(index) {
            val response = restTemplate.getForEntity("$DND5_API_URL/races/$index", Race::class.java)
            when {
                response.statusCode.is2xxSuccessful -> response.body
                else -> throw InvalidRace()
            }
        } as Race
    }
}