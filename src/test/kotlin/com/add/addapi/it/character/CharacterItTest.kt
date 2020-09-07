package com.add.addapi.it.character

import com.add.addapi.attribute.responses.AttributeResponse
import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.responses.CharacterNicknameResponse
import com.add.addapi.character.responses.CharacterReferenceResponse
import com.add.addapi.character.responses.CharacterResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class CharacterItTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `create character with valid data`() {
        val nickname = "Nickname${System.currentTimeMillis()}"
        val newCharacterRequest = createNewCharacterRequest(nickname)
        val response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isCreated)
                .andReturn().response

        val createdCharacterResponse = mapper.readValue<CharacterReferenceResponse>(response.contentAsString)

        mockMvc.perform(
                MockMvcRequestBuilders.get(createdCharacterResponse.url)
        ).andExpect(status().isOk)
    }

    @Test
    fun `verify nickname exists`() {
        val nickname = "Nickname${System.currentTimeMillis()}"
        val newCharacterRequest = createNewCharacterRequest(nickname)
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isCreated)
                .andReturn().response

        val response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/characters/exists/${nickname}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andReturn().response
        val characterNicknameResponse = mapper.readValue<CharacterNicknameResponse>(response.contentAsString)
        assertThat(characterNicknameResponse.nickname).isEqualTo(nickname)
        assertThat(characterNicknameResponse.exists).isTrue()
    }

    @Test
    fun `verify nickname does not exist`() {
        val nickname = "Nickname${System.currentTimeMillis()}"

        val response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/characters/exists/${nickname}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andReturn().response
        val characterNicknameResponse = mapper.readValue<CharacterNicknameResponse>(response.contentAsString)
        assertThat(characterNicknameResponse.nickname).isEqualTo(nickname)
        assertThat(characterNicknameResponse.exists).isFalse()
    }

    @Test
    fun `get character by nickname`() {
        val nickname = "Nickname${System.currentTimeMillis()}"
        val newCharacterRequest = createNewCharacterRequest(nickname)
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isCreated)

        val response = mockMvc.perform(
                MockMvcRequestBuilders.get("/characters/${nickname}")
        ).andExpect(status().isOk)
                .andReturn().response

        val character = mapper.readValue<CharacterResponse>(response.contentAsString)
        assertThat(character.nickname).isEqualTo(nickname)
        assertThat(character.name).isEqualTo("Xeresa")
        assertThat(character.age).isEqualTo(38)
        assertThat(character.race.name).isEqualTo("Elf")
        assertThat(character.mainClass.name).isEqualTo("Bard")
        assertThat(character.subClass.name).isEqualTo("Lore")
        assertThat(character.equipments.size).isEqualTo(1)
        assertThat(character.equipments[0].name).isEqualTo("Amulet")
        assertThat(character.spells.size).isEqualTo(1)
        assertThat(character.spells[0].name).isEqualTo("Acid Arrow")
    }

    @Test
    fun `list`() {
        val nickname = "Nickname${System.currentTimeMillis()}"
        val newCharacterRequest = createNewCharacterRequest(nickname)
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isCreated)
                .andReturn().response

        val response = mockMvc.perform(
                MockMvcRequestBuilders.get("/characters")
        ).andExpect(status().isOk)
                .andReturn().response

        val characters = mapper.readValue<List<CharacterReferenceResponse>>(response.contentAsString)
        assertThat(characters.size).isGreaterThan(0)
        assertThat(characters[0].id).isNotNull()
        assertThat(characters[0].nickname).isEqualTo(nickname)
        assertThat(characters[0].url).isEqualTo("/characters/${nickname}")

    }

    @Test
    fun `get character by nickname when does not exist`() {
        val nickname = "Nickname${System.currentTimeMillis()}"
        val response = mockMvc.perform(
                MockMvcRequestBuilders.get("/characters/${nickname}")
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `create character with existent nickname throws bad request`() {
        val nickname = "Nickname${System.currentTimeMillis()}"
        val newCharacterRequest = createNewCharacterRequest(nickname)
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isCreated)
                .andReturn().response

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isBadRequest)
                .andReturn().response
    }

    @Test
    fun `create character with invalid attribute throws bad request`() {
        val newCharacterRequest = NewCharacterRequest(
                nickname = "Nickname${System.currentTimeMillis()}",
                name = "Xeresa",
                age = 38,
                race = NewCharacterRequest.AttributeReference("invalid"),
                mainClass = NewCharacterRequest.AttributeReference("invalid"),
                subClass = NewCharacterRequest.AttributeReference("invalid"),
                equipments = listOf(NewCharacterRequest.AttributeReference("invalid")),
                spells = listOf(NewCharacterRequest.AttributeReference("invalid"))
        )
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isBadRequest)
    }

    private fun createNewCharacterRequest(nickname: String): NewCharacterRequest {
        return NewCharacterRequest(
                nickname = nickname,
                name = "Xeresa",
                age = 38,
                race = NewCharacterRequest.AttributeReference("elf"),
                mainClass = NewCharacterRequest.AttributeReference("bard"),
                subClass = NewCharacterRequest.AttributeReference("lore"),
                equipments = listOf(NewCharacterRequest.AttributeReference("amulet")),
                spells = listOf(NewCharacterRequest.AttributeReference("acid-arrow"))
        )
    }


}