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
        val mainClass: Characteristic,
        val subClass: Characteristic,
        val race: Characteristic,
        val equipment: List<Characteristic>,
        val spells: List<Characteristic>

) {
    data class Characteristic(
            val id: String,
            val name: String
    )
}