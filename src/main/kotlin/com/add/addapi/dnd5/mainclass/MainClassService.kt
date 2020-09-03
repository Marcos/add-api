package com.add.addapi.dnd5.mainclass

import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class MainClassService(
        val apiRepository: ApiRepository
) {

    fun getByIndex(index: String): MainClass {
        return apiRepository.getByIndex(index, "classes", MainClass::class.java) as MainClass
    }
}