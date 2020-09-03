package com.add.addapi.dnd5.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
open class InvalidResource(index: String, resource: String, exception: Exception? = null) :
        RuntimeException("Invalid index $index for resource $resource", exception)
