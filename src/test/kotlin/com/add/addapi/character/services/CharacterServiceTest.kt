package com.add.addapi.character.services

import com.add.addapi.character.model.Character
import com.add.addapi.character.repositories.CharacterRepository
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.exceptions.InvalidAgeException
import com.add.addapi.character.exceptions.RequiredFieldException
import com.add.addapi.dnd5.services.Dnd5ListService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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
    private lateinit var dnd5ListService: Dnd5ListService

    @InjectMockKs
    private lateinit var characterService: CharacterService

    @BeforeAll
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `createCharater when has valid data`() {
        val id = UUID.randomUUID().toString()
        val newCharacterRequest = NewCharacterRequest(
                nickname = "Nickname",
                name = "Xeresa",
                age = 38,
                mainClass = "main",
                subClass = "sub",
                race = "human",
                spells = listOf("spell"),
                equipments = listOf("equipment")
        )
        val characterDocument = Character(
                id = id,
                nickname = "Nickname",
                name = "Xeresa",
                age = 38,
                mainClass = Character.Characteristic("main", "main"),
                subClass = Character.Characteristic("sub", "sub"),
                race = Character.Characteristic("human", "human"),
                spells = listOf(Character.Characteristic("spell", "spell")),
                equipment = listOf(Character.Characteristic("equipment", "equipment"))
        )
        every {
            characterRepository.save(any<Character>())
        } returns characterDocument

        characterService.save(newCharacterRequest)

        verify(exactly = 1) {
            characterRepository.save(any<Character>())
        }
    }

    @Test
    fun `createCharater when required fields are empty throws exception`() {
        val id = UUID.randomUUID().toString()
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
            characterService.save(newCharacterRequest)
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
            characterService.save(newCharacterRequest)
        }
    }

}