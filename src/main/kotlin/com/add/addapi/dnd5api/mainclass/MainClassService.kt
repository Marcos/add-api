package com.add.addapi.dnd5api.mainclass

import com.add.addapi.dnd5api.api.AttributeType.MAINCLASS
import com.add.addapi.dnd5api.api.MainClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class MainClassService(
        val apiRepository: ApiRepository
) {

    fun getByIndex(index: String): MainClass {
        return apiRepository.getByIndex(index, MAINCLASS) as MainClass
    }
}