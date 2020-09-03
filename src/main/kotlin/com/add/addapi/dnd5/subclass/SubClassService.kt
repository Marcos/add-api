package com.add.addapi.dnd5.subclass

import com.add.addapi.dnd5.api.MainClass
import com.add.addapi.dnd5.api.SubClass
import org.springframework.stereotype.Service

@Service
class SubClassService(
        val subClassRepository: SubClassRepository
) {

    fun getByIndex(index: String, mainClass: MainClass): SubClass {
        return when (mainClass.subclasses.find { it.index == index }) {
            null -> throw InvalidSubClass()
            else -> subClassRepository.getByIndex(index)
        }
    }
}