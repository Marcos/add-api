package com.add.addapi.character.services

import com.add.addapi.character.model.Character
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.exceptions.InvalidAgeException
import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.dnd5.equipment.EquipmentService
import com.add.addapi.dnd5.mainclass.MainClassService
import com.add.addapi.dnd5.race.RaceService
import com.add.addapi.dnd5.spell.SpellService
import com.add.addapi.dnd5.subclass.SubClassService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CharacterServiceTest {

    @MockK(relaxed = true)
    private lateinit var characterRepository: CharacterRepository

    @MockK(relaxed = true)
    private lateinit var raceService: RaceService

    @MockK(relaxed = true)
    private lateinit var mainClassService: MainClassService

    @MockK(relaxed = true)
    private lateinit var subClassService: SubClassService

    @MockK(relaxed = true)
    private lateinit var equipmentService: EquipmentService

    @MockK(relaxed = true)
    private lateinit var spellService: SpellService

    @InjectMockKs
    private lateinit var characterService: CharacterService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `createCharater when has valid data`() {
        val newCharacterRequest = createNewCharacterRequest()
        val characterDocumentId = UUID.randomUUID().toString()
        val savedCharacter = createCharacter(characterDocumentId)
        every {
            characterRepository.save(any<Character>())
        } returns savedCharacter

        val savedId = characterService.create(newCharacterRequest)

        assertThat(savedId).isEqualTo(characterDocumentId)
        verify(exactly = 1) {
            raceService.getByIndex(newCharacterRequest.race)
            mainClassService.getByIndex(newCharacterRequest.mainClass)
            subClassService.getByIndex(newCharacterRequest.subClass, any())
            equipmentService.getByIndexes(newCharacterRequest.equipments)
            spellService.getByIndexes(newCharacterRequest.spells, any(), any())
            characterRepository.save(any<Character>())
        }
    }

    private fun createNewCharacterRequest(): NewCharacterRequest {
        return NewCharacterRequest(
                nickname = "Nickname",
                name = "Xeresa",
                age = 38,
                mainClass = "main",
                subClass = "sub",
                race = "human",
                spells = listOf("spell"),
                equipments = listOf("equipment")
        )
    }

    private fun createCharacter(characterDocumentId: String): Character {
        return Character(
                id = characterDocumentId,
                nickname = "Nickname",
                name = "Xeresa",
                age = 38,
                mainClass = Character.Characteristic("main", "main", "main"),
                subClass = Character.Characteristic("sub", "sub", "sub"),
                race = Character.Characteristic("human", "human", "human"),
                spells = listOf(Character.Characteristic("spell", "spell", "spell")),
                equipment = listOf(Character.Characteristic("equipment", "equipment", "equipment"))
        )
    }

    @Test
    fun `createCharater when required fields are empty throws exception`() {
        val newCharacterRequest = NewCharacterRequest(
                nickname = "",
                name = "",
                age = 38,
                mainClass = "",
                subClass = "",
                race = "",
                spells = emptyList(),
                equipments = emptyList()
        )

        assertThrows<RequiredFieldException> {
            characterService.create(newCharacterRequest)
        }
    }

    @Test
    fun `createCharater when age is not valid`() {
        val id = UUID.randomUUID().toString()
        val newCharacterRequest = NewCharacterRequest(
                nickname = "Nickname",
                name = "Xeresa",
                age = -10,
                mainClass = "main",
                subClass = "sub",
                race = "human",
                spells = listOf("spell"),
                equipments = listOf("equipment")
        )

        assertThrows<InvalidAgeException> {
            characterService.create(newCharacterRequest)
        }
    }

}