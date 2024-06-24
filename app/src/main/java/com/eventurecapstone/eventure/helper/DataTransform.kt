package com.eventurecapstone.eventure.helper

import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.entity.EventResultPascal
import com.eventurecapstone.eventure.data.entity.Recommend

object DataTransform {
    fun fromRecommendToEvents(data: Recommend): EventResult {
        return EventResult(
            id = data.id,
            category = data.category,
            city = data.locationCity,
            title = data.title,
            latitude = data.latitude?.replace(",", ".")?.toDouble(),
            longitude = data.longitude?.replace(",", ".")?.toDouble(),
            date = data.eventStart,
            fullAddress = data.locationAddress
        )
    }
    fun fromRecommendListToEventResultList(data: List<Recommend?>?): List<EventResult>? {
        if (data != null) {
            return data.map { fromRecommendToEvents(it!!) }
        }
        return null
    }
    fun fromEventPascalToEvents(data: EventResultPascal): EventResult {
        return EventResult(
            title = data.title,
            category = data.category,
            date = data.date,
            city = data.city,
            fullAddress = data.fullAddress,
            latitude = data.latitude,
            longitude = data.longitude,

            id = data.id,
            authorId = data.authorId,
            author = data.author,
            description = data.description,
            banner = data.banner,
            distance = data.distance,

            favorite = data.favorite,
            link = data.link,

            createdAt = data.createdAt,
            updatedAt = data.updatedAt
        )
    }
    fun fromEventPascalListToEventsList(data: List<EventResultPascal?>?): List<EventResult>? {
        if (data != null) {
            return data.map { fromEventPascalToEvents(it!!) }
        }
        return null
    }
}