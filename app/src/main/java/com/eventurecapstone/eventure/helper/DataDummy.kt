package com.eventurecapstone.eventure.helper

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.entity.EventDetailResponse
import com.eventurecapstone.eventure.data.entity.EventResponse

object DataDummy {
    private var eventList = mutableListOf(
        Event(
            id = 1,
            title = "taylor swift",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.924970,
            longitude = 110.142390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        Event(
            id = 2,
            title = "BMTH",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.964970,
            longitude = 110.192390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        Event(
            id = 3,
            title = "A7F",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.924970,
            longitude = 110.112390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        Event(
            id = 4,
            title = "SO7",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.934970,
            longitude = 110.152390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
    )

    fun getEvent(): EventResponse?{
        return EventResponse(
            success = true,
            message = "getting data successfully",
            event = eventList
        )
    }

    fun getEventDetail(id: Int): EventDetailResponse? {
        return EventDetailResponse(
            success = true,
            message = "getting event detail for id $id",
            event = eventList[id-1]
        )
    }

    fun addNewEvent(event: Event): EventDetailResponse? {
        eventList.add(event)

        return EventDetailResponse(
            success = true,
            message = "add event success",
            event = eventList[eventList.size - 1]
        )
    }

    fun deleteEvent(id: Int): BasicResponse? {
        eventList.removeAt(id-1)
        return BasicResponse(
            success = true,
            message = "delete data successfully"
        )
    }

    fun updateEvent(id: Int, event: Event) : BasicResponse? {
        eventList[id - 1] = event
        return BasicResponse(
            success = true,
            message = "update data successfully"
        )
    }

    fun removeFromFavorite(id: Int): BasicResponse? {
        eventList[id - 1].favorite = false
        return BasicResponse(
            success = true,
            message = "remove data from favorite successfully"
        )
    }

    fun addToFavorite(id: Int): BasicResponse? {
        eventList[id - 1].favorite = true
        return BasicResponse(
            success = true,
            message = "remove data from favorite successfully"
        )
    }
}