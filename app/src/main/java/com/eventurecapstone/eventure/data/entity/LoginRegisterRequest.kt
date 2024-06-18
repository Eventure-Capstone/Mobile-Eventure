package com.eventurecapstone.eventure.data.entity

data class LoginRegisterRequest(
    val email: String,
    val password: String,
    val name: String? = null,
)
