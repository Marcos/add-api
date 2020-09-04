package com.add.addapi.race

import com.add.addapi.dnd5api.AttributeType.RACE
import com.add.addapi.dnd5api.Race
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class RaceService(
        val apiRepository: ApiRepository
) {

    fun getByIndex(index: String): Race {
        return apiRepository.getByIndex(index, RACE) as Race
    }
}