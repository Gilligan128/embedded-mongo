package com.henke.embeddedmongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmbeddedMongoApplication

fun main(args: Array<String>) {
	runApplication<EmbeddedMongoApplication>(*args)
}
