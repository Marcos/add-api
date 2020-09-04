package com.add.addapi.dnd5api.subclass

import com.add.addapi.dnd5api.api.AttributeType.SUBCLASS
import com.add.addapi.dnd5api.api.MainClass
import com.add.addapi.dnd5api.api.SubClass
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class SubClassService(
        val apiRepository: ApiRepository
) {

    fun getByIndex(index: String, mainClass: MainClass): SubClass {
        return when (mainClass.subclasses.find { it.index == index }) {
            null -> throw SubClassNotAllowedException(index, mainClass)
            else -> apiRepository.getByIndex(index, SUBCLASS) as SubClass
        }
    }
}