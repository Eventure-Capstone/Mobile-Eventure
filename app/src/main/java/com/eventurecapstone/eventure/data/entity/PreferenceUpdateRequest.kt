package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class PreferenceUpdateRequest(
    @field:SerializedName("preference_ids") val preferenceIds: List<String>
)
