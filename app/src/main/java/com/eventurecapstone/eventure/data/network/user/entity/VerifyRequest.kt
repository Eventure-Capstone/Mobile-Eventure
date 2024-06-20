package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("otp_code")
    val otp_code: String
)
