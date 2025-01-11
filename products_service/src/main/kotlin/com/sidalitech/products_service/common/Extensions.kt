package com.sidalitech.products_service.common

import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.reflect.full.memberProperties

fun <T : Any> T.toNonNullMap(): Map<String, Any> {
    return this::class
        .memberProperties
        .filter { it.getter.call(this) != null } // Filter out null properties
        .associate { it.name to it.getter.call(this)!! } // Map property names to non-null values
}

fun <T : Any> T.toNullableMap(): Map<String, Any> {
    return this::class
        .memberProperties
        .associate { it.name to it.getter.call(this)!! } // Map property names to non-null values
}

fun <T> T.toFilterMap(vararg excludeFields: String): MutableMap<String, Any> {
    val mapper = ObjectMapper()
    val map = mapper.convertValue(this, Map::class.java) as Map<String, Any>
    return map.filterKeys { it !in excludeFields }.toMutableMap()
}

