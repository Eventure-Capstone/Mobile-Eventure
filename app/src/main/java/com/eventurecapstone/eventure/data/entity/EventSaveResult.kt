package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class EventSaveResult(
    @field:SerializedName("user_id") val userId: String?,
    @field:SerializedName("event_id") val eventId: String?
)
