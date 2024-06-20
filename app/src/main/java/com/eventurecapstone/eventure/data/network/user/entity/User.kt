package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("preferences")
	val preferences: List<String?>? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("saved_events")
	val savedEvents: List<String?>? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("events")
	val events: List<String?>? = null
)
