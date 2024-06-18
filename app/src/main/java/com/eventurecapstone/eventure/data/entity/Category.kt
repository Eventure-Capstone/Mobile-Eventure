package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class Category(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("picture_url")
    val pictureUrl: String? = null,
)
