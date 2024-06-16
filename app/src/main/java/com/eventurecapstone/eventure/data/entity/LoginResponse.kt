package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("loginResult")
    val loginResult: Login? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)