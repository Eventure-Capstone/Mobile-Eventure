package com.eventurecapstone.eventure.data.network.event.entity

import com.google.gson.annotations.SerializedName

data class RecommendResponse(

	@field:SerializedName("data")
	val data: List<Recommend?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Recommend(

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Location_City")
	val locationCity: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("Latitude")
	val latitude: String? = null,

	@field:SerializedName("Event_Start")
	val eventStart: String? = null,

	@field:SerializedName("Location_Address")
	val locationAddress: String? = null,

	@field:SerializedName("Longitude")
	val longitude: String? = null
)
