package com.add.addapi.equipment

import com.add.addapi.dnd5api.AttributeType.EQUIPMENT
import com.add.addapi.dnd5api.Equipment
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
        val apiRepository: ApiRepository
) {

    fun getByIndexes(indexes: List<String>): List<Equipment> {
        return apiRepository.getByIndexes(indexes, EQUIPMENT) as List<Equipment>
    }
}