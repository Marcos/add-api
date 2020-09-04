package com.add.addapi.character.services

import com.add.addapi.character.exceptions.CharacterNotFoundException
import com.add.addapi.character.model.Character
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.responses.CharacterResponse
import com.add.addapi.race.RaceService
import com.add.addapi.character.exceptions.InvalidAgeException
import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.equipment.EquipmentService
import com.add.addapi.mainclass.MainClassService
import com.add.addapi.spell.SpellService
import com.add.addapi.subclass.SubClassService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterService(
        val characterRepository: CharacterRepository,
        val raceService: RaceService,
        val mainClassService: MainClassService,
        val subClassService: SubClassService,
        val equipmentService: EquipmentService,
        val spellService: SpellService
) {

    companion object {
        private const val DESC_SEPARATOR: String = "\\n\\n"
    }

    fun create(newCharacterRequest: NewCharacterRequest): String {
        validate(newCharacterRequest)
        val race = raceService.getByIndex(newCharacterRequest.race)
        val mainClass = mainClassService.getByIndex(newCharacterRequest.mainClass)
        val subClass = subClassService.getByIndex(newCharacterRequest.subClass, mainClass)
        val equipments = equipmentService.getByIndexes(newCharacterRequest.equipments)
        val spells = spellService.getByIndexes(newCharacterRequest.spells, mainClass, subClass)
        val savedCharacter = characterRepository.save(Character(
                id = UUID.randomUUID().toString(),
                nickname = newCharacterRequest.nickname,
                name = newCharacterRequest.name,
                age = newCharacterRequest.age,
                race = Character.Attribute(race.index, race.name, "${race.age}${DESC_SEPARATOR}${race.alignment}${DESC_SEPARATOR}${race.language_desc}"),
                mainClass = Character.Attribute(mainClass.index, mainClass.name, ""),
                subClass = Character.Attribute(subClass.index, subClass.name, joinDesc(subClass.desc)),
                equipment = equipments.map { Character.Attribute(it.index, it.name, joinDesc(it.desc)) },
                spells = spells.map { Character.Attribute(it.index, it.name, joinDesc(it.desc)) }
        ))
        return savedCharacter.id
    }

    private fun joinDesc(it: List<*>) = it.joinToString(separator = DESC_SEPARATOR)

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

    fun get(id: String): CharacterResponse {
        val character = characterRepository.findById(id)
                .orElseThrow { throw CharacterNotFoundException() }
        return CharacterResponse(
                id = character.id,
                name = character.name
        )
    }
}