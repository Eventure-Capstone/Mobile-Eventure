package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("loginResult")
    val loginResult: Login? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)