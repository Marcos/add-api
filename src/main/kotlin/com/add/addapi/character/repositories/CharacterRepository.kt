package com.add.addapi.character.repositories

import com.add.addapi.character.model.Character
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CharacterRepository : MongoRepository<Character, String> {

    fun findByNickname(nickname: String): Optional<Character>

}