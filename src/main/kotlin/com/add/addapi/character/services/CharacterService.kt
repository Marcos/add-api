package com.add.addapi.character.services

import com.add.addapi.CharacterNotFoundException
import com.add.addapi.character.model.Character
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.responses.CharacterResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterService(
        val characterRepository: CharacterRepository
) {

    fun save(newCharacterRequest: NewCharacterRequest): String {
        val savedCharacter = characterRepository.save(Character(
                id = UUID.randomUUID().toString(),
                nickname = newCharacterRequest.nickname,
                name = newCharacterRequest.name,
                age = newCharacterRequest.age,
                equipments = getCharacteristc(newCharacterRequest.equipments),
                mainClass = getCharacteristc(newCharacterRequest.mainClass),
                subClass = getCharacteristc(newCharacterRequest.subClass),
                race = getCharacteristc(newCharacterRequest.race),
                spells = getCharacteristc(newCharacterRequest.spells)
        ))
        return savedCharacter.id
    }

    private fun getCharacteristc(items: List<String>): List<Character.CharacterData> {
        return items.map { Character.CharacterData(it, it) }
    }

    private fun getCharacteristc(item: String): Character.CharacterData {
        return Character.CharacterData(item, item)
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