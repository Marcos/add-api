package com.add.addapi.dnd5.equipment

import com.add.addapi.dnd5.api.Equipment
import org.springframework.stereotype.Service

@Service
class EquipmentService(
        val equipmentRepository: EquipmentRepository
) {

    fun getByIndexes(indexes: List<String>): List<Equipment> {
        return equipmentRepository.getByIndexes(indexes)
    }
}