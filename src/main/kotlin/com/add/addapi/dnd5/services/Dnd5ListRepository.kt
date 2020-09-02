package com.add.addapi.dnd5.services

import com.add.addapi.dnd5.responses.Dnd5ListResponse
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit


@Service
class Dnd5ListRepository(
        val restTemplate: RestTemplate
) {
    private companion object {
        const val DND5_API_URL = "https://www.dnd5eapi.co/api/"

        private val dnd5Cache: Cache<String, Dnd5ListResponse> = Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .maximumSize(10)
                .build()
    }

    fun getList(key: String): Dnd5ListResponse? {
        return dnd5Cache.get(key) {
            val response = restTemplate.getForEntity("${DND5_API_URL}/$it", Dnd5ListResponse::class.java)
            response.body!!
        }
    }
}