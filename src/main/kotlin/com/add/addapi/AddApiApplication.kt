package com.add.addapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class AddApiApplication

fun main(args: Array<String>) {
	runApplication<AddApiApplication>(*args)
}
