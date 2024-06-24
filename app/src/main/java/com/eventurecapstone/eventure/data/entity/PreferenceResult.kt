package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class PreferenceResult(
    @field:SerializedName("user_id") val userId: String,
    @field:SerializedName("preference_id") val preferenceId : String,
)
