package com.add.addapi.dnd5.spell

import com.add.addapi.dnd5.api.Equipment
import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.api.Spell
import com.add.addapi.dnd5.api.SubClass
import com.add.addapi.dnd5.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class SpellService(
        val apiRepository: ApiRepository
) {

    fun getByIndexes(indexes: List<String>, mainClass: MainClass, subClass: SubClass): List<Spell> {
        val spells = apiRepository.getByIndexes(indexes, "spells", Spell::class.java) as List<Spell>

        spells.forEach { spell ->
            val spellClasses = spell.classes.map { it.index }
            val spellSubClasses = spell.subclasses.map { it.index }
            if (!spellClasses.contains(mainClass.index) && !spellSubClasses.contains(subClass.index)) {
                throw SpellClassException()
            }
        }

        return spells
    }
}