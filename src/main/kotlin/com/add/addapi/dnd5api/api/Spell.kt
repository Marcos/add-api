package com.add.addapi.dnd5api.api

data class Spell(
        val index: String,
        val name: String,
        val desc: List<String> = emptyList(),
        val classes: List<NamedAPIResource> = emptyList(),
        val subclasses: List<NamedAPIResource> = emptyList()
) : ApiResource