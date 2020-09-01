package com.add.addapi.character.repositories

import com.add.addapi.character.model.Character
import org.springframework.data.mongodb.repository.MongoRepository

interface CharacterRepository: MongoRepository<Character, String> {
}