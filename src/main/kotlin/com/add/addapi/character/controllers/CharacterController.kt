package com.add.addapi.character.controllers

import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.responses.CharacterResponse
import com.add.addapi.character.responses.NewCreatedCharacterResponse
import com.add.addapi.character.services.CharacterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/characters")
class CharacterController(
        val characterService: CharacterService
) {

    @PostMapping
    fun save(@RequestBody newCharacterRequest: NewCharacterRequest): ResponseEntity<NewCreatedCharacterResponse> {
        val id = characterService.save(newCharacterRequest)
        return ResponseEntity(
                NewCreatedCharacterResponse(id = id, url = "/characters/$id"),
                HttpStatus.CREATED
        )
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): CharacterResponse {
        return characterService.get(id)
    }

}