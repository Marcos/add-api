package com.add.addapi.dnd5api.api

data class Equipment(
        val index: String,
        val name: String,
        val desc: List<String>
) : ApiResource