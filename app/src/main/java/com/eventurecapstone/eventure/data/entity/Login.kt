package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class Login (
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)