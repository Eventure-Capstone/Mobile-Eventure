package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class EventSaveRequest(
    @field:SerializedName("event_id") val eventId: String
)
