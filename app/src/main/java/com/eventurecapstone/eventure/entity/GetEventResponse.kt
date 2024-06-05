package com.eventurecapstone.eventure.entity

import com.google.gson.annotations.SerializedName

data class GetEventResponse(

	@field:SerializedName("Event")
	val event: List<Event?>? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

