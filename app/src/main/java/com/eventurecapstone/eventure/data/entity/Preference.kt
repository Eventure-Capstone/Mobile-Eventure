package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class Preference(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("picture_url") val pictureUrl: String?
)
