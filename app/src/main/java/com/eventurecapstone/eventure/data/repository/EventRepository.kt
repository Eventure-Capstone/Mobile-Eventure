package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.entity.EventDetailResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.helper.DataDummy

class EventRepository {

    suspend fun getEventRecommendation(): EventResponse? {
        return DataDummy.getEvent()
    }

    suspend fun getEventBySearch(searchValue: String): EventResponse? {
        return DataDummy.getEvent()
    }

    suspend fun getSavedEvent(isUpcoming: Boolean): EventResponse? {
        return DataDummy.getEvent()
    }

    suspend fun getDetailEvent(idEvent: Int): EventDetailResponse? {
        return DataDummy.getEventDetail(idEvent)
    }

    suspend fun addEventToFavorite(idEvent: Int): BasicResponse? {
        return DataDummy.addToFavorite(idEvent)
    }

    suspend fun removeEventFromFavorite(idEvent: Int): BasicResponse? {
        return DataDummy.removeFromFavorite(idEvent)
    }

    suspend fun getOwnEvent(): EventResponse? {
        return DataDummy.getEvent()
    }

    suspend fun addNewEvent(event: Event): EventDetailResponse? {
        return DataDummy.addNewEvent(event)
    }

    suspend fun updateOwnEvent(idEvent: Int, event: Event): BasicResponse? {
        return DataDummy.updateEvent(idEvent, event)
    }

    suspend fun deleteOwnEvent(idEvent: Int): BasicResponse? {
        return DataDummy.deleteEvent(idEvent)
    }

    companion object {
        @Volatile
        private var INSTANCE: EventRepository? = null

        fun getInstance() : EventRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = EventRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}