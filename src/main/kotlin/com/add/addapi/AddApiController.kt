package com.add.addapi

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AddApiController {

    @GetMapping("/health")
    fun get(): ResponseEntity<String> {
        return ResponseEntity("OK", HttpStatus.OK)
    }

    @GetMapping("/")
    fun index(): ResponseEntity<String> {
        return ResponseEntity("Hey, this is the add-api!", HttpStatus.OK)
    }
}