package com.add.addapi.dnd5.services

import com.add.addapi.character.model.Character
import com.add.addapi.dnd5.exceptions.CharacteristicNotFoundException
import com.add.addapi.enums.Characteristics
import org.springframework.stereotype.Service

@Service
class Dnd5ListService(
        val dnd5ListRepository: Dnd5ListRepository
) {

    fun getCharacteristic(index: String, characteristic: Characteristics): Character.Characteristic {
        val resultList = dnd5ListRepository.getList(characteristic.resource)
        val entry = resultList!!.results.find { it.index == index }
        if (entry != null) {
            return Character.Characteristic(entry.index, entry.name)
        }
        throw CharacteristicNotFoundException(index, characteristic)
    }
}