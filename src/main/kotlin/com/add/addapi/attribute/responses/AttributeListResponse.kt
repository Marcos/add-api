package com.add.addapi.attribute.responses

data class AttributeListResponse(
        val count: Int,
        val results: List<AttributeResponse>
)