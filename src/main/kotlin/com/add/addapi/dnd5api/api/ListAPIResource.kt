package com.add.addapi.dnd5api.api

data class ListAPIResource(
        val count: Long,
        val results: List<NamedAPIResource>
) : ApiResource