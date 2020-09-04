package com.add.addapi.dnd5api.api

import com.fasterxml.jackson.annotation.JsonProperty

data class SubClass(
        val index: String,
        val name: String,
        val desc: List<String> = emptyList(),
        @JsonProperty("class")
        val mainClass: NamedAPIResource? = null
) : ApiResource {
    fun toNamedAPIResource() = NamedAPIResource(
            index = index,
            name = name,
            url = "classes/$index"
    )
}