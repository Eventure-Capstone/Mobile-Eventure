package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName

data class SaveCategoryResponse(

	@field:SerializedName("data")
	val data: CategoryCount? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CategoryCount(

	@field:SerializedName("count")
	val count: Int? = null
)
