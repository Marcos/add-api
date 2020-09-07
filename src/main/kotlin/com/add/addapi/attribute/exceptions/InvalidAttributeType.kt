package com.add.addapi.attribute.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidAttributeType(exception: Exception) : RuntimeException(exception)