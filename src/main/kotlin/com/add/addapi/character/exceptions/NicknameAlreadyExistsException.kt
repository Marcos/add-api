package com.add.addapi.character.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NicknameAlreadyExistsException : RuntimeException("Nickname already exists")
