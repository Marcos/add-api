package com.add.addapi.repositories

import com.add.addapi.dnd5api.api.*
import com.add.addapi.exceptions.InvalidResource
import com.add.addapi.repositories.ApiCache.apiCache
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class ApiRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndexes(indexes: List<String>, attributeType: AttributeType): List<ApiResource> {
        return indexes.map { getByIndex(it, attributeType) }
    }

    fun getByIndex(index: String, attributeType: AttributeType): ApiResource {
        return apiCache.get(index) {
            fetchItem(index, attributeType) as ApiResource
        } as ApiResource
    }

    fun list(type: AttributeType): ApiResource {
        return apiCache.get(type.toString()) {
            fetchList(type) as ApiResource
        } as ApiResource
    }

    private fun fetchList(attributeType: AttributeType): Any {
        val url = "$DND5_API_URL/${attributeType.resource}"
        val exceptionMessage = "Invalid resource ${attributeType.toString().toLowerCase()}"
        return fetch(url, ListAPIResource::class.java, exceptionMessage)
    }

    private fun fetchItem(index: String, attributeType: AttributeType): Any {
        val url = "$DND5_API_URL/${attributeType.resource}/$index"
        val exceptionMessage = "Invalid index $index for resource ${attributeType.toString().toLowerCase()}"
        return fetch(url, attributeType.classType, exceptionMessage)
    }

    private fun fetch(url: String, type: Class<*>, exceptionMessage: String): Any {
        try {
            val response = restTemplate.getForEntity(
                    url,
                    type
            )
            when {
                response.statusCode.is2xxSuccessful -> return response.body!!
                else -> throw InvalidResource(exceptionMessage)
            }
        } catch (exception: Exception) {
            throw InvalidResource(exceptionMessage, exception)
        }
    }

}