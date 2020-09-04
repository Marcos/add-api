package com.add.addapi.attribute.controllers

import com.add.addapi.attribute.responses.AttributeListResponse
import com.add.addapi.attribute.services.AttributeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/attributes")
class AttributeController(
        val attributeService: AttributeService
) {

    @GetMapping
    fun listTypes(): List<String> {
        return attributeService.listTypes()
    }

    @GetMapping("/{type}")
    fun listByType(@PathVariable("type") type: String): AttributeListResponse {
        return attributeService.listByType(type)
    }

}