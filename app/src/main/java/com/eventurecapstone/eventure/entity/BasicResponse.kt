package com.eventurecapstone.eventure.entity

import com.google.gson.annotations.SerializedName

data class BasicResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
