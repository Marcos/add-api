package com.add.addapi.character.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class CharacterNotFoundException : RuntimeException("Character not found")