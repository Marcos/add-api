package com.add.addapi.dnd5api.repositories

import com.add.addapi.configurations.logger
import com.add.addapi.dnd5api.ApiResource
import com.add.addapi.dnd5api.AttributeType
import com.add.addapi.dnd5api.DND5_API_URL
import com.add.addapi.dnd5api.ListAPIResource
import com.add.addapi.exceptions.InvalidResource
import com.add.addapi.dnd5api.repositories.ApiCache.apiCache
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.util.logging.Level

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
        val response = get(url, type, exceptionMessage)
        when {
            response.statusCode.is2xxSuccessful -> return response.body!!
            else -> throw InvalidResource(exceptionMessage)
        }
    }

    private fun get(url: String, type: Class<*>, exceptionMessage: String): ResponseEntity<out Any> {
        try {
            return restTemplate.getForEntity(
                    url,
                    type
            )
        } catch (exception: Exception) {
            logger().log(Level.SEVERE, "Error getting resource", exception)
            throw InvalidResource(exceptionMessage, exception)
        }

    }

}