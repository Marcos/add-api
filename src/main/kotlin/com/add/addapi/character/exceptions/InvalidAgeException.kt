package com.add.addapi.character.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Age should be bigger than zero")
class InvalidAgeException : RuntimeException("Age should be bigger than zero")