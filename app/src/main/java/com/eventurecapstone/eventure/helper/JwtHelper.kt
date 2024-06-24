package com.eventurecapstone.eventure.helper

import com.auth0.android.jwt.JWT

object JwtHelper {
    fun decodeJwtToken(token: String?, columnWanted: String): String? {
        if (token == null) return null
        return try { JWT(token).getClaim(columnWanted).asString() } catch (e: Exception) { null }
    }
}