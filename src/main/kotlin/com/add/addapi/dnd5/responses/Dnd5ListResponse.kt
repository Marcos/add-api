package com.add.addapi.dnd5.responses

data class Dnd5ListResponse(
        val results: List<Dnd5Entry>,
        val count: Long
) {
    data class Dnd5Entry(
            val index: String,
            val name: String,
            val url: String
    )
}