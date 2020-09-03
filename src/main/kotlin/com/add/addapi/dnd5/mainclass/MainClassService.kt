package com.add.addapi.dnd5.mainclass

import com.add.addapi.dnd5.api.MainClass
import org.springframework.stereotype.Service

@Service
class MainClassService(
        val mainClassRepository: MainClassRepository
) {

    fun getByIndex(index: String): MainClass {
        return mainClassRepository.getByIndex(index)
    }
}