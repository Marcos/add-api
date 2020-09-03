package com.add.addapi.dnd5.spell

import com.add.addapi.dnd5.api.ApiCache.apiCache
import com.add.addapi.dnd5.api.DND5_API_URL
import com.add.addapi.dnd5.api.Spell
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate


@Repository
class SpellRepository(
        val restTemplate: RestTemplate
) {

    fun getByIndex(index: String): Spell {
        return apiCache.get(index) {
            val response = restTemplate.getForEntity("$DND5_API_URL/spells/$index", Spell::class.java)
            when {
                response.statusCode.is2xxSuccessful -> response.body
                else -> throw InvalidSpell()
            }
        } as Spell
    }

    fun getByIndexes(indexes: List<String>): List<Spell> {
        return indexes.map { getByIndex(it) }
    }
}