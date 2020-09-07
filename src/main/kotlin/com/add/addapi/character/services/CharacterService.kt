package com.add.addapi.character.services

import com.add.addapi.character.exceptions.CharacterNotFoundException
import com.add.addapi.character.exceptions.InvalidAgeException
import com.add.addapi.character.exceptions.NicknameAlreadyExistsException
import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.character.model.Character
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.responses.CharacterNicknameResponse
import com.add.addapi.character.responses.CharacterReferenceResponse
import com.add.addapi.character.responses.CharacterResponse
import com.add.addapi.dnd5api.Race
import com.add.addapi.equipment.EquipmentService
import com.add.addapi.mainclass.MainClassService
import com.add.addapi.race.RaceService
import com.add.addapi.spell.SpellService
import com.add.addapi.subclass.SubClassService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.*
import org.springframework.data.domain.Sort.Direction.DESC
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
    fun getByNickname(nickname: String): CharacterResponse {
        val character = characterRepository.findByNickname(nickname)
                .orElseThrow { throw CharacterNotFoundException() }
        return character.toCharacterResponse()
    }

    fun verifyNicknameExists(nickname: String): CharacterNicknameResponse {
        return CharacterNicknameResponse(nickname, nicknameExists(nickname))
    }

    fun list(): List<CharacterReferenceResponse> {
        val page = PageRequest.of(0, 10, by(Order.desc("createdAt")))
        return characterRepository.findAll(page)
                .content
                .map { it.toCharacterReferenceResponse() }
    }

    fun create(newCharacterRequest: NewCharacterRequest): CharacterReferenceResponse {
        validate(newCharacterRequest)
        val character = getCharacterData(newCharacterRequest)
        val savedCharacter = characterRepository.save(character)
        return savedCharacter.toCharacterReferenceResponse()
    }

    private fun getCharacterData(newCharacterRequest: NewCharacterRequest): Character {
        val race = raceService.getByIndex(newCharacterRequest.race.id)
        val mainClass = mainClassService.getByIndex(newCharacterRequest.mainClass.id)
        val subClass = subClassService.getByIndex(newCharacterRequest.subClass.id, mainClass)
        val equipments = equipmentService.getByIndexes(getIndexes(newCharacterRequest.equipments))
        val spells = spellService.getByIndexes(getIndexes(newCharacterRequest.spells), mainClass, subClass)
        return Character(
                id = UUID.randomUUID().toString(),
                nickname = newCharacterRequest.nickname,
                name = newCharacterRequest.name,
                age = newCharacterRequest.age,
                race = Character.Attribute(race.index, race.name, getRaceDescription(race)),
                mainClass = Character.Attribute(mainClass.index, mainClass.name, ""),
                subClass = Character.Attribute(subClass.index, subClass.name, joinDesc(subClass.desc)),
                equipments = equipments.map { Character.Attribute(it.index, it.name, joinDesc(it.desc)) },
                spells = spells.map { Character.Attribute(it.index, it.name, joinDesc(it.desc)) }
        )
    }

    private fun nicknameExists(nickname: String) = characterRepository.findByNickname(nickname).isPresent

    private fun getRaceDescription(race: Race) =
            "${race.age} ${race.alignment} ${race.language_desc}"

    private fun getIndexes(indexes: List<NewCharacterRequest.AttributeReference>?) =
            indexes?.map { it.id } ?: emptyList()

    private fun joinDesc(it: List<String>?) = it?.joinToString(separator = " ") ?: ""

    private fun validate(newCharacterRequest: NewCharacterRequest) {
        if (newCharacterRequest.age <= 0)
            throw InvalidAgeException()
        if (newCharacterRequest.nickname.isNullOrEmpty() ||
                newCharacterRequest.name.isNullOrEmpty() ||
                newCharacterRequest.race.id.isNullOrEmpty() ||
                newCharacterRequest.mainClass.id.isNullOrEmpty() ||
                newCharacterRequest.subClass.id.isNullOrEmpty()
        )
            throw RequiredFieldException()
        if(nicknameExists(newCharacterRequest.nickname))
            throw NicknameAlreadyExistsException()
    }

}