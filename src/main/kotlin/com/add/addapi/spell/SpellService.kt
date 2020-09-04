package com.add.addapi.spell

import com.add.addapi.attribute.responses.AttributeListResponse
import com.add.addapi.attribute.responses.AttributeResponse
import com.add.addapi.attribute.services.AttributeService
import com.add.addapi.dnd5api.AttributeType.SPELL
import com.add.addapi.dnd5api.MainClass
import com.add.addapi.dnd5api.Spell
import com.add.addapi.dnd5api.SubClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class SpellService(
        val attributeService: AttributeService,
        val apiRepository: ApiRepository
) {

    fun getByIndexes(indexes: List<String>, mainClass: MainClass, subClass: SubClass): List<Spell> {
        val spells = apiRepository.getByIndexes(indexes, SPELL) as List<Spell>

        spells.forEach { spell ->
            val spellClasses = spell.classes.map { it.index }
            val spellSubClasses = spell.subclasses.map { it.index }
            if (!spellClasses.contains(mainClass.index) && !spellSubClasses.contains(subClass.index)) {
                throw SpellNotAllowedException()
            }
        }

        return spells
    }

    fun list(mainClassIndex: String, subClassIndex: String): AttributeListResponse {
        val spells = getAllSpells()
        val allowedSpells = spells.filter { spell ->
            spell.classes.map { it.index }.contains(mainClassIndex) ||
                    spell.subclasses.map { it.index }.contains(mainClassIndex)
        }
        return AttributeListResponse(
                count = allowedSpells.size,
                results = allowedSpells.map { AttributeResponse(it.index, it.name) }
        )
    }

    fun getAllSpells(): List<Spell> {
        val spellIndexList = attributeService.listByType(SPELL)
        return apiRepository.getByIndexes(
                spellIndexList.results.map { it.index }, SPELL
        ) as List<Spell>
    }
}