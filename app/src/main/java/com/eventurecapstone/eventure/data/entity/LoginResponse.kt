package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("success") val success: Boolean,
    @field:SerializedName("message") val message: String,
    @field:SerializedName("data") val data: LoginResult?
)