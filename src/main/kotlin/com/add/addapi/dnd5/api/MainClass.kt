package com.add.addapi.dnd5.api

data class MainClass(
        val index: String,
        val name: String,
        val subclasses: List<NamedAPIResource> = emptyList()
) : ApiResource {
    fun toNamedAPIResource() = NamedAPIResource(
            index = index,
            name = name,
            url = "classes/$index"
    )
}