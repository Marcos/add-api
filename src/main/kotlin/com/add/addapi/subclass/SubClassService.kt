package com.add.addapi.subclass

import com.add.addapi.attribute.responses.AttributeListResponse
import com.add.addapi.attribute.responses.AttributeResponse
import com.add.addapi.dnd5api.AttributeType.SUBCLASS
import com.add.addapi.dnd5api.MainClass
import com.add.addapi.dnd5api.SubClass
import com.add.addapi.mainclass.MainClassService
import com.add.addapi.dnd5api.repositories.ApiRepository
import org.springframework.stereotype.Service

@Service
class SubClassService(
        val apiRepository: ApiRepository,
        val mainClassService: MainClassService
) {

    fun getByIndex(index: String, mainClass: MainClass): SubClass {
        return when (mainClass.subclasses.find { it.index == index }) {
            null -> throw SubClassNotAllowedException(index, mainClass)
            else -> apiRepository.getByIndex(index, SUBCLASS) as SubClass
        }
    }

    fun listByMainClass(mainClassIndex: String): AttributeListResponse {
        val mainClass = mainClassService.getByIndex(mainClassIndex)
        return AttributeListResponse(
                count = mainClass.subclasses.size,
                results = mainClass.subclasses.map { AttributeResponse(it.index, it.name) }
        )
    }
}