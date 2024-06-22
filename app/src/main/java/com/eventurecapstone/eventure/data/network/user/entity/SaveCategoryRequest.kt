package com.eventurecapstone.eventure.data.network.user.entity

import com.google.gson.annotations.SerializedName

data class SaveCategoryRequest(

	@field:SerializedName("preference_ids")
	val preferenceIds: List<String?>? = null
)
