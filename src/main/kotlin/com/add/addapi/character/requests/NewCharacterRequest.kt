package com.add.addapi.character.requests

data class NewCharacterRequest(
        val nickname: String,
        val name: String,
        val age: Int,
        val mainClass: AttributeReference,
        val subClass: AttributeReference,
        val race: AttributeReference,
        val equipments: List<AttributeReference>?,
        val spells: List<AttributeReference>?
) {

    data class AttributeReference(val id: String)

}