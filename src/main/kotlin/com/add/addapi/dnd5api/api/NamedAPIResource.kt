package com.add.addapi.dnd5api.api

data class NamedAPIResource(
        val index: String,
        val url: String,
        val name: String
) : ApiResource