package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class EventDetailResponse(

    @field:SerializedName("Event")
    val event: Event? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("success")
    val success: Boolean? = null
)
