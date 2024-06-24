package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("token") val token: String
)