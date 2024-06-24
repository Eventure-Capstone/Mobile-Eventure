package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class OtpRequest(
    @field:SerializedName("email") val email: String,
    @field:SerializedName("otp_code") val otpCode: String
)