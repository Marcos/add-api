package com.add.addapi.configurations

import com.add.addapi.spell.SpellService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Schedule(
        val spellService: SpellService
) {
    
    @Scheduled(fixedRate = 7200000)
    fun spellsPreFetch() {
        logger().info("Prefetching spells")
        spellService.getAllSpells()
    }

}