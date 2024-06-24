package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class OtpResult (
    @field:SerializedName("id") val id: String,
    @field:SerializedName("otp_code") val otpCode: String,
    @field:SerializedName("created_at") val createdAt: String,
    @field:SerializedName("expires_at") val expiresAt: String,
    @field:SerializedName("is_used") val isUsed: Boolean,
    @field:SerializedName("user_id") val userId: String
)