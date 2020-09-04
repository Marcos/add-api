package com.add.addapi.dnd5api

const val DND5_API_URL = "https://www.dnd5eapi.co/api"

interface ApiResource

enum class AttributeType(val resource: String, val classType: Class<*>) {

    RACE("races", Race::class.java),
    MAINCLASS("classes", MainClass::class.java),
    SUBCLASS("subclasses", SubClass::class.java),
    EQUIPMENT("equipment", Equipment::class.java),
    SPELL("spells", Spell::class.java)

}