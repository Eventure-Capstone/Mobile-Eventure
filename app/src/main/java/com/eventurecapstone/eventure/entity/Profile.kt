package com.eventurecapstone.eventure.entity

import com.google.gson.annotations.SerializedName

data class Profile(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("picture_url")
    val pictureUrl: String? = null,

    @field:SerializedName("verified")
    val verified: Boolean? = false,
)
