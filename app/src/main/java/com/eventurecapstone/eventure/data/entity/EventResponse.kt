package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class EventResponse(

	@field:SerializedName("Event")
	val event: List<Event?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

