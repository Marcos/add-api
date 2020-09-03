package com.add.addapi.dnd5.race

import com.add.addapi.dnd5.api.Race
import com.add.addapi.dnd5.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class RaceService(
        val apiRepository: ApiRepository
) {

    fun getByIndex(index: String): Race {
        return apiRepository.getByIndex(index, "races", Race::class.java) as Race
    }
}