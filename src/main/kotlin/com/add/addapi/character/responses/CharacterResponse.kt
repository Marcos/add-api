package com.add.addapi.character.responses

data class CharacterResponse(
        val id: String,
        val nickname: String,
        val name: String,
        val age: Int,
        val race: CharacterAttributeResponse,
        val mainClass: CharacterAttributeResponse,
        val subClass: CharacterAttributeResponse,
        val equipments: List<CharacterAttributeResponse>,
        val spells: List<CharacterAttributeResponse>
){

    data class CharacterAttributeResponse(
            val id: String,
            val name: String,
            val description: String? = null
    )
}