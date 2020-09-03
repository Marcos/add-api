package com.add.addapi.dnd5.race

import com.add.addapi.dnd5.api.Race
import org.springframework.stereotype.Service

@Service
class RaceService(
        val raceRepository: RaceRepository
) {

    fun getByIndex(index: String): Race {
        return raceRepository.getByIndex(index)
    }
}