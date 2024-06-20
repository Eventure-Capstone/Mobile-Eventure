package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName


// RegisterRequest.kt
data class RegisterRequest(
    val full_name: String,
    val email: String,
    val password: String
)

// RegisterResponse.kt
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val data: RegisterData?
)

data class RegisterData(
    val id: String,
    val email: String,
    val full_name: String,
    val password: String,
    val is_active: Boolean,
    val profile_picture: String?,
    val created_at: String,
    val updated_at: String
)

// VerifyRequest.kt
data class VerifyRequest(
    val email: String,
    val otp_code: String
)

// VerifyResponse.kt
// TODO: kalau error belum tau responsenya
data class VerifyResponse(
    val success: Boolean,
    val message: String,
    val data: VerifyData?
)

data class VerifyData(
    val id: String,
    val email: String,
    val full_name: String,
    val password: String,
    val is_active: Boolean,
    val profile_picture: String?,
    val created_at: String,
    val updated_at: String,
    val email_verifications: List<EmailVerification>
)

data class EmailVerification(
    val id: String,
    val otp_code: String,
    val created_at: String,
    val expires_at: String,
    val is_used: Boolean,
    val user_id: String
)
