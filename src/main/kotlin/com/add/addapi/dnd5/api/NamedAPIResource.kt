package com.add.addapi.dnd5.api

data class NamedAPIResource(
        val index: String,
        val url: String,
        val name: String
) : ApiResource