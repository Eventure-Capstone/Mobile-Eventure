package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class RecommendRequest (
    @field:SerializedName("Category")
    val category: List<String>,

    @field:SerializedName("Location_City")
    val locationCity: String
)