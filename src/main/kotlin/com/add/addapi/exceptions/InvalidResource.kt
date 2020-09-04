package com.add.addapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
open class InvalidResource(message: String, exception: Exception? = null) :
        RuntimeException(message, exception)
