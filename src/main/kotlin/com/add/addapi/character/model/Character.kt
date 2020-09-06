package com.add.addapi.character.model

import com.add.addapi.character.responses.CharacterReferenceResponse
import com.add.addapi.character.responses.CharacterResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Character(
        @Id
        val id: String,
        @Indexed(unique = true)
        val nickname: String,
        val name: String,
        val age: Int,
        val mainClass: Attribute,
        val subClass: Attribute,
        val race: Attribute,
        val equipments: List<Attribute>,
        val spells: List<Attribute>,
        val createdAt: LocalDateTime = LocalDateTime.now()

) {
    data class Attribute(
            val id: String,
            val name: String,
            val description: String
    ) {
        fun toCharacterAttributeResponse() = CharacterResponse.CharacterAttributeResponse(
                id = id,
                name = name,
                description = description
        )
    }

    fun toCharacterResponse() = CharacterResponse(
            id = id,
            name = name,
            nickname = nickname,
            age = age,
            race = race.toCharacterAttributeResponse(),
            mainClass = mainClass.toCharacterAttributeResponse(),
            subClass = subClass.toCharacterAttributeResponse(),
            spells = spells.map { it.toCharacterAttributeResponse() },
            equipments = equipments.map { it.toCharacterAttributeResponse() }
    )

    fun toCharacterReferenceResponse() = CharacterReferenceResponse(
            id = id,
            nickname = nickname,
            url = "/characters/${nickname}"
    )

}