package com.add.addapi.dnd5.equipment

import com.add.addapi.dnd5.api.ApiCache.apiCache
import com.add.addapi.dnd5.api.DND5_API_URL
import com.add.addapi.dnd5.api.Equipment
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate


@Repository
class EquipmentRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndex(index: String): Equipment {
        return apiCache.get(index) {
            val response = restTemplate.getForEntity("$DND5_API_URL/equipment/$index", Equipment::class.java)
            when {
                response.statusCode.is2xxSuccessful -> response.body
                else -> throw InvalidEquipment()
            }
        } as Equipment
    }

    fun getByIndexes(indexes: List<String>): List<Equipment> {
        return indexes.map { getByIndex(it) }
    }
}