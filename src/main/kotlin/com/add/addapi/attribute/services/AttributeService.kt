package com.add.addapi.attribute.services

import com.add.addapi.attribute.exceptions.InvalidAttributeType
import com.add.addapi.attribute.responses.AttributeListResponse
import com.add.addapi.attribute.responses.AttributeResponse
import com.add.addapi.dnd5api.AttributeType
import com.add.addapi.dnd5api.ListAPIResource
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class AttributeService(
        val apiRepository: ApiRepository
) {

    fun listTypes(): List<String> {
        return AttributeType.values().map { it.toString().toLowerCase() }
    }

    fun listByType(type: String): AttributeListResponse {
        return listByType(getSafeAttributeType(type))
    }

    fun listByType(type: AttributeType): AttributeListResponse {
        val listAPIResource = apiRepository.list(type) as ListAPIResource
        return AttributeListResponse(
                count = listAPIResource.count,
                results = listAPIResource.results.map { AttributeResponse(it.index, it.name) }
        )
    }

    private fun getSafeAttributeType(type: String): AttributeType {
        try {
            return AttributeType.valueOf(type.toUpperCase())
        } catch (exception: IllegalArgumentException) {
            throw InvalidAttributeType(exception)
        }
    }
}