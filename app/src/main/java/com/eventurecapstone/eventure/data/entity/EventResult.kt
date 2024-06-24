package com.eventurecapstone.eventure.data.entity

import com.google.gson.annotations.SerializedName

data class EventResult(
    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("category") val category: String? = null,
    @field:SerializedName("date") val date: String? = null, // it receive event_start too
    @field:SerializedName("city") val city: String? = null, // it receive Location_City too
    @field:SerializedName("full_address") val fullAddress: String? = null, // it receive Location_Address too
    @field:SerializedName("latitude") var latitude: Double? = null,
    @field:SerializedName("longitude") var longitude: Double? = null,

    @field:SerializedName("id") var id: String? = null,
    @field:SerializedName("author_id") var authorId: String? = null,
    @field:SerializedName("author") var author: UserResult? = null,
    @field:SerializedName("description") val description: String? = null,
    @field:SerializedName("banner") var banner: String? = null,
    @field:SerializedName("distance") var distance: Double? = null,

    @field:SerializedName("favorite") var favorite: Boolean? = false,
    @field:SerializedName("social_link") val link: Any? = null, // TODO: change this type after BE ready

    @field:SerializedName("created_at") val createdAt: String? = null,
    @field:SerializedName("updated_at") val updatedAt: String? = null
)

data class EventResultPascal(
    @field:SerializedName("Title") val title: String? = null,
    @field:SerializedName("Category") val category: String? = null,
    @field:SerializedName("Event_Start") val date: String? = null, // it receive event_start too
    @field:SerializedName("Location_City") val city: String? = null, // it receive Location_City too
    @field:SerializedName("Location_Address") val fullAddress: String? = null, // it receive Location_Address too
    @field:SerializedName("Latitude") var latitude: Double? = null,
    @field:SerializedName("Longitude") var longitude: Double? = null,

    @field:SerializedName("Id") var id: String? = null,
    @field:SerializedName("Author_Id") var authorId: String? = null,
    @field:SerializedName("Author") var author: UserResult? = null,
    @field:SerializedName("Description") val description: String? = null,
    @field:SerializedName("Banner") var banner: String? = null,
    @field:SerializedName("Distance") var distance: Double? = null,

    @field:SerializedName("Favorite") var favorite: Boolean? = false,
    @field:SerializedName("Social_Link") val link: Any? = null, // TODO: change this type after BE ready

    @field:SerializedName("Created_At") val createdAt: String? = null,
    @field:SerializedName("Updated_At") val updatedAt: String? = null
)