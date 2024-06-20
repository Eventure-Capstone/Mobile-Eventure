package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName

data class NearbyResponse(

	@field:SerializedName("data")
	val data: List<Nearby?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Nearby(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("banner")
	val banner: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("full_address")
	val fullAddress: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("author_id")
	val authorId: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)
