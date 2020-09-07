package com.add.addapi.configurations

import com.add.addapi.spell.SpellService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Schedule(
        val spellService: SpellService
) {

    companion object {
        const val TWO_HOURS: Long = 7200000
    }

    @Scheduled(fixedRate = TWO_HOURS)
    fun spellsPreFetch() {
        logger().info("Prefetching spells")
        spellService.getAllSpells()
    }

}