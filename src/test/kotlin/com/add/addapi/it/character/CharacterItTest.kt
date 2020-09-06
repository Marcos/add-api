package com.add.addapi.it.character

import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.responses.CharacterReferenceResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
        val newCharacterRequest = NewCharacterRequest(
                nickname = "Nickname${System.currentTimeMillis()}",
                name = "Xeresa",
                age = 38,
                race = NewCharacterRequest.AttributeReference("elf"),
                mainClass = NewCharacterRequest.AttributeReference("bard"),
                subClass = NewCharacterRequest.AttributeReference("lore"),
                equipments = listOf(NewCharacterRequest.AttributeReference("amulet")),
                spells = listOf(NewCharacterRequest.AttributeReference("acid-arrow"))
        )
        val response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andExpect(status().isCreated)
                .andReturn().response

        val createdCharacterResponse = mapper.readValue<CharacterReferenceResponse>(
                response.contentAsString
        )

        mockMvc.perform(
                MockMvcRequestBuilders.get(createdCharacterResponse.url)
        ).andExpect(status().isOk)
    }

    @Test
    fun `create character with invalid resource`() {
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

}