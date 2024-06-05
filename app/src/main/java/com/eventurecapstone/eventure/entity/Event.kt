package com.eventurecapstone.eventure.entity

import com.google.gson.annotations.SerializedName

data class Event(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("publisher")
    val publisher: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("picture_url")
    val pictureUrl: String? = null
)
