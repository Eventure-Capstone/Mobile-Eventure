package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class UserResult(
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("email") val email: String?,
    @field:SerializedName("full_name") val fullName: String?,
    @field:SerializedName("password") val password: String?,
    @field:SerializedName("is_active") val isActive: Boolean? = false,
    @field:SerializedName("profile_picture") val profilePicture: String?,
    @field:SerializedName("created_at") val createdAt: String,
    @field:SerializedName("updated_at") val updatedAt: String,
    @field:SerializedName("email_verifications") val otpResult: List<OtpResult>?,
    @field:SerializedName("preferences") val preferences: List<PreferenceResult>?,
    @field:SerializedName("events") val events: List<EventResult>?, // TODO: waiting for BE
    @field:SerializedName("saved_events") val savedEvents: List<EventSaveResult>?,

    )