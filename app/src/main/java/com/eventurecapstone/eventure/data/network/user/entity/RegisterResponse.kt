package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("date")
    val data: RegisterData?
)

data class RegisterData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("full_name")
    val full_name: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("is_active")
    val is_active: Boolean,

    @field:SerializedName("profile_picture")
    val profile_picture: String?,

    @field:SerializedName("created_at")
    val created_at: String,

    @field:SerializedName("updated_at")
    val updated_at: String
)