package com.add.addapi.character.requests

data class NewCharacterRequest(
        val nickname: String,
        val name: String,
        val age: Int,
        val mainClass: String,
        val subClass: String,
        val race: String,
        val equipments: List<String>,
        val spells: List<String>
)