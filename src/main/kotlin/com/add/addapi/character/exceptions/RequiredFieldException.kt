package com.add.addapi.character.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class RequiredFieldException() : RuntimeException("Fields can not be null or empty")