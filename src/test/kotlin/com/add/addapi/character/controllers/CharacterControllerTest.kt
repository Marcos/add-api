package com.add.addapi.character.controllers

import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.responses.NewCreatedCharacterResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class CharacterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createCharacter() {
        val newCharacterRequest = NewCharacterRequest(
                nickname = "Nickname${System.currentTimeMillis()}",
                name = "Xeresa",
                age = 38,
                mainClass = "main",
                subClass = "sub",
                race = "human",
                spells = listOf("spell"),
                equipments = listOf("equipment")
        )
        val createdCharacterResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(newCharacterRequest))
        ).andReturn()
        assertThat(createdCharacterResult.response.status).isEqualTo(HttpStatus.CREATED.value())

        val createdCharacterResponse = mapper.readValue<NewCreatedCharacterResponse>(
                createdCharacterResult.response.contentAsString
        )

        val getCreatedCharacterResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(createdCharacterResponse.url)
        ).andReturn()
        assertThat(getCreatedCharacterResult.response.status).isEqualTo(HttpStatus.OK.value())
    }

}