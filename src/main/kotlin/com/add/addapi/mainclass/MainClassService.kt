package com.add.addapi.mainclass

import com.add.addapi.dnd5api.AttributeType.MAINCLASS
import com.add.addapi.dnd5api.MainClass
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