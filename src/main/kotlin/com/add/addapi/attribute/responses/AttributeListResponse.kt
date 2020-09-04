package com.add.addapi.attribute.responses

data class AttributeListResponse(
        val count: Long,
        val results: List<AttributeResponse>
)