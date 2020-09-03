package com.add.addapi.dnd5.api

import com.fasterxml.jackson.annotation.JsonProperty

const val DND5_API_URL = "https://www.dnd5eapi.co/api"

interface ApiResource

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

data class MainClass(
        val index: String,
        val name: String,
        val subclasses: List<NamedAPIResource>
) : ApiResource

data class SubClass(
        val index: String,
        val name: String,
        val desc: List<String>,
        @JsonProperty("class")
        val mainClass: NamedAPIResource
) : ApiResource

data class Equipment(
        val index: String,
        val name: String,
        val desc: List<String>
) : ApiResource

data class Spell(
        val index: String,
        val name: String,
        val desc: List<String>,
        val classes: List<NamedAPIResource>,
        val subclasses: List<NamedAPIResource>
) : ApiResource

data class NamedAPIResource(
        val index: String,
        val url: String,
        val name: String
) : ApiResource
