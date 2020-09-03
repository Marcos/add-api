package com.add.addapi.dnd5.equipment

import com.add.addapi.dnd5.api.Equipment
import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
        val apiRepository: ApiRepository
) {

    fun getByIndexes(indexes: List<String>): List<Equipment> {
        return apiRepository.getByIndexes(indexes, "equipment", Equipment::class.java) as List<Equipment>
    }
}