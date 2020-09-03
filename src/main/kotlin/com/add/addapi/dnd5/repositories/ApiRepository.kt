package com.add.addapi.dnd5.repositories

import com.add.addapi.dnd5.api.ApiResource
import com.add.addapi.dnd5.api.DND5_API_URL
import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.api.Race
import com.add.addapi.dnd5.exceptions.InvalidResource
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class ApiRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndexes(indexes: List<String>, resource: String, type: Class<*>): List<Any> {
        return indexes.map { getByIndex(it, resource, type) }
    }

    fun getByIndex(index: String, resource: String, type: Class<*>): ApiResource {
        return ApiCache.apiCache.get(index) {
            fetch(index, resource, type) as ApiResource
        } as ApiResource
    }

    private fun fetch(index: String, resource: String, type: Class<*>): Any {
        try {
            val response = restTemplate.getForEntity("$DND5_API_URL/$resource/$index", type)
            when {
                response.statusCode.is2xxSuccessful -> return response.body!!
                else -> throw InvalidResource(index, resource)
            }
        } catch (exception: Exception) {
            throw InvalidResource(index, resource, exception)
        }
    }


}