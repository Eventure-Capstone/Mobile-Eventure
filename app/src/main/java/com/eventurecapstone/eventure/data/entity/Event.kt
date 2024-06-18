package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class Event(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("picture_url")
	val pictureUrl: String? = null,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("longitude")
	val longitude: Double,

	@field:SerializedName("start_date")
	val startDate: String,

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("end_time")
	val endTime: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("favorite")
	var favorite: Boolean? = false,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("link")
	val link: String? = null,
)
