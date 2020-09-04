package com.add.addapi.dnd5api

data class ListAPIResource(
        val count: Int,
        val results: List<NamedAPIResource>
) : ApiResource