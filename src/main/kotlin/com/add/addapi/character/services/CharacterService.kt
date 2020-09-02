package com.add.addapi.character.services

import com.add.addapi.character.exceptions.CharacterNotFoundException
import com.add.addapi.character.model.Character
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.responses.CharacterResponse
import com.add.addapi.dnd5.services.Dnd5ListService
import com.add.addapi.character.exceptions.InvalidAgeException
import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.enums.Characteristics
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterService(
        val characterRepository: CharacterRepository,
        val dnd5ListService: Dnd5ListService
) {

    fun save(newCharacterRequest: NewCharacterRequest): String {
        validate(newCharacterRequest)
        val savedCharacter = characterRepository.save(Character(
                id = UUID.randomUUID().toString(),
                nickname = newCharacterRequest.nickname,
                name = newCharacterRequest.name,
                age = newCharacterRequest.age,
                race = dnd5ListService.getCharacteristic(newCharacterRequest.race, Characteristics.RACE),
                mainClass = dnd5ListService.getCharacteristic(newCharacterRequest.mainClass, Characteristics.MAIN_CLASS),
                subClass = dnd5ListService.getCharacteristic(newCharacterRequest.subClass, Characteristics.SUBCLASS),
                equipment = getCharacteristc(newCharacterRequest.equipments),
                spells = getCharacteristc(newCharacterRequest.spells)
        ))
        return savedCharacter.id
    }

    private fun validate(newCharacterRequest: NewCharacterRequest) {
        if (newCharacterRequest.age <= 0)
            throw InvalidAgeException()
        if (newCharacterRequest.nickname.isNullOrEmpty() ||
                newCharacterRequest.name.isNullOrEmpty() ||
                newCharacterRequest.race.isNullOrEmpty() ||
                newCharacterRequest.mainClass.isNullOrEmpty() ||
                newCharacterRequest.subClass.isNullOrEmpty()
        )
            throw RequiredFieldException()
    }

    private fun getCharacteristc(items: List<String>): List<Character.Characteristic> {
        return items.map { Character.Characteristic(it, it) }
    }

    fun get(id: String): CharacterResponse {
        val character = characterRepository.findById(id)
                .orElseThrow { throw CharacterNotFoundException() }
        return CharacterResponse(
                id = character.id,
                name = character.name
        )
    }
}