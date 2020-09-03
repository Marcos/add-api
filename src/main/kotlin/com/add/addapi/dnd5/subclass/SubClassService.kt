package com.add.addapi.dnd5.subclass

import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.api.Race
import com.add.addapi.dnd5.api.SubClass
import com.add.addapi.dnd5.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class SubClassService(
        val apiRepository: ApiRepository
) {

    fun getByIndex(index: String, mainClass: MainClass): SubClass {
        return when (mainClass.subclasses.find { it.index == index }) {
            null -> throw SubClassNotAllowed(index, mainClass)
            else -> apiRepository.getByIndex(index, "subclasses", SubClass::class.java) as SubClass
        }
    }
}