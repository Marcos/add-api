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
        val mainClass: Attribute,
        val subClass: Attribute,
        val race: Attribute,
        val equipment: List<Attribute>,
        val spells: List<Attribute>

) {
    data class Attribute(
            val id: String,
            val name: String,
            val description: String
    )
}