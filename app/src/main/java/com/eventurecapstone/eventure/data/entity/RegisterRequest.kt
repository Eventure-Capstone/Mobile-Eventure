package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("full_name") val fullName: String,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("password") val password: String
)