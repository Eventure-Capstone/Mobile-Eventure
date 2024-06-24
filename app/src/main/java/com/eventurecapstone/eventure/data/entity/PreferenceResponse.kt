package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class PreferenceResponse(

    @field:SerializedName("data")
	val data: List<Preference?>? = null,

    @field:SerializedName("success")
	val success: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)
