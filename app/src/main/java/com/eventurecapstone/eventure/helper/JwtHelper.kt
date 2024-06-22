package com.eventurecapstone.eventure.helper

import com.auth0.android.jwt.JWT

object JwtHelper {
    fun decodeJwtToken(token: String?, columnWanted: String): String? {
        if (token == null){
            return null
        }

        return try {
            val jwt = JWT(token)
            val userId = jwt.getClaim(columnWanted).asString()
            userId
        } catch (e: Exception) {
            // Handle exception
            null
        }
    }
}