package com.add.addapi.it.attribute

import com.add.addapi.attribute.responses.AttributeListResponse
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class AttributeItTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun `get attribute list for valid type`() {
        val response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/attributes/race")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
                .andReturn().response

        val attributeListResponse = mapper.readValue<AttributeListResponse>(
                response.contentAsString
        )

        assertThat(attributeListResponse.count).isGreaterThan(0)
        assertThat(attributeListResponse.results.size).isGreaterThan(0)
    }

    @Test
    fun `get attribute list for invalid type`() {
        val response = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/attributes/invalidtype")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
    }

}