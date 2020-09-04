package com.add.addapi.dnd5api.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Race(
        val index: String,
        val name: String,
        val age: String,
        val alignment: String,
        @JsonProperty("size_description")
        val sizeDescription: String,
        @JsonProperty("language_desc")
        val language_desc: String
) : ApiResource