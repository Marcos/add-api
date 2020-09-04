package com.add.addapi.dnd5.api

data class Equipment(
        val index: String,
        val name: String,
        val desc: List<String>
) : ApiResource