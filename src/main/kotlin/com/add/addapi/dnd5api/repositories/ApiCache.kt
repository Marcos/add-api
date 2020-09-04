package com.add.addapi.dnd5api.repositories

import com.add.addapi.dnd5api.ApiResource
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit


object ApiCache {
    val apiCache: Cache<String, ApiResource> = Caffeine.newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .maximumSize(3000)
            .build()
}
