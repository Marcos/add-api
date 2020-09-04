package com.add.addapi.dnd5api.spell

import com.add.addapi.dnd5api.api.AttributeType.SPELL
import com.add.addapi.dnd5api.api.MainClass
import com.add.addapi.dnd5api.api.Spell
import com.add.addapi.dnd5api.api.SubClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class SpellService(
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
}