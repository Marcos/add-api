package com.add.addapi.character.services

import com.add.addapi.character.model.Character
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.exceptions.InvalidAgeException
import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.equipment.EquipmentService
import com.add.addapi.mainclass.MainClassService
import com.add.addapi.race.RaceService
import com.add.addapi.spell.SpellService
import com.add.addapi.subclass.SubClassService
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
    fun `create character when has valid data`() {
        val newCharacterRequest = createNewCharacterRequest()
        val characterDocumentId = UUID.randomUUID().toString()
        val savedCharacter = createCharacter(characterDocumentId)
        every {
            characterRepository.save(any<Character>())
        } returns savedCharacter

        val character = characterService.create(newCharacterRequest)

        assertThat(character.id).isEqualTo(characterDocumentId)
        verify(exactly = 1) {
            raceService.getByIndex(newCharacterRequest.race.id)
            mainClassService.getByIndex(newCharacterRequest.mainClass.id)
            subClassService.getByIndex(newCharacterRequest.subClass.id, any())
            equipmentService.getByIndexes(newCharacterRequest.equipments.map { it.id })
            spellService.getByIndexes(newCharacterRequest.spells.map { it.id }, any(), any())
            characterRepository.save(any<Character>())
        }
    }

    private fun createNewCharacterRequest(): NewCharacterRequest {
        return NewCharacterRequest(
                nickname = "Nickname",
                name = "Xeresa",
                age = 38,
                mainClass = NewCharacterRequest.AttributeReference("main"),
                subClass =  NewCharacterRequest.AttributeReference("sub"),
                race =  NewCharacterRequest.AttributeReference("human"),
                spells = listOf( NewCharacterRequest.AttributeReference("spell")),
                equipments = listOf( NewCharacterRequest.AttributeReference("equipment"))
        )
    }

    private fun createCharacter(characterDocumentId: String): Character {
        return Character(
                id = characterDocumentId,
                nickname = "Nickname",
                name = "Xeresa",
                age = 38,
                mainClass = Character.Attribute("main", "main", "main"),
                subClass = Character.Attribute("sub", "sub", "sub"),
                race = Character.Attribute("human", "human", "human"),
                spells = listOf(Character.Attribute("spell", "spell", "spell")),
                equipments = listOf(Character.Attribute("equipment", "equipment", "equipment"))
        )
    }

    @Test
    fun `create character when required fields are empty throws exception`() {
        val newCharacterRequest = NewCharacterRequest(
                nickname = "",
                name = "",
                age = 38,
                mainClass = NewCharacterRequest.AttributeReference(""),
                subClass = NewCharacterRequest.AttributeReference(""),
                race = NewCharacterRequest.AttributeReference(""),
                spells = emptyList(),
                equipments = emptyList()
        )

        assertThrows<RequiredFieldException> {
            characterService.create(newCharacterRequest)
        }
    }

    @Test
    fun `create character when age is not valid`() {
        val newCharacterRequest = NewCharacterRequest(
                nickname = "Nickname",
                name = "Xeresa",
                age = -10,
                mainClass = NewCharacterRequest.AttributeReference("main"),
                subClass =  NewCharacterRequest.AttributeReference("sub"),
                race =  NewCharacterRequest.AttributeReference("human"),
                spells = listOf( NewCharacterRequest.AttributeReference("spell")),
                equipments = listOf( NewCharacterRequest.AttributeReference("equipment"))
        )

        assertThrows<InvalidAgeException> {
            characterService.create(newCharacterRequest)
        }
    }

}