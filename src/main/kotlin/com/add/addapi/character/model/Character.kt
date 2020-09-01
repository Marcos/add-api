package com.add.addapi.character.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Character(
        @Id
        val id: String,
        @Indexed(unique = true)
        val nickname: String,
        val name: String,
        val age: Int,
        val mainClass: CharacterData,
        val subClass: CharacterData,
        val race: CharacterData,
        val equipments: List<CharacterData>,
        val spells: List<CharacterData>

) {
    data class CharacterData(
            val id: String,
            val name: String
    )
}