package com.add.addapi.character.controllers

import com.add.addapi.character.requests.NewCharacterRequest
import com.add.addapi.character.responses.CharacterNicknameResponse
import com.add.addapi.character.responses.CharacterReferenceResponse
import com.add.addapi.character.responses.CharacterResponse
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
    fun create(@RequestBody newCharacterRequest: NewCharacterRequest): ResponseEntity<CharacterReferenceResponse> {
        val characterResponse = characterService.create(newCharacterRequest)
        return ResponseEntity(characterResponse, HttpStatus.CREATED)
    }

    @GetMapping
    fun list(): List<CharacterReferenceResponse> {
        return characterService.list()
    }

    @GetMapping("/{nickname}")
    fun get(@PathVariable("nickname") nickname: String): CharacterResponse {
        return characterService.getByNickname(nickname)
    }

    @GetMapping("/exists/{nickname}")
    fun verifyNicknameExists(@PathVariable("nickname") nickname: String): CharacterNicknameResponse {
        return characterService.verifyNicknameExists(nickname)
    }

}