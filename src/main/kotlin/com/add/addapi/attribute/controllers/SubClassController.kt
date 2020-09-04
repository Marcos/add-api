package com.add.addapi.attribute.controllers

import com.add.addapi.attribute.responses.AttributeListResponse
import com.add.addapi.subclass.SubClassService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subclasses")
class SubClassController(
        val subClassService: SubClassService
) {
    @GetMapping("/{mainClassIndex}")
    fun listByType(@PathVariable("mainClassIndex") mainClassIndex: String): AttributeListResponse {
        return subClassService.listByMainClass(mainClassIndex)
    }

}