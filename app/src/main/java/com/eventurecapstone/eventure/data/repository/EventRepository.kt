package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.entity.EventDetailResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.helper.DataDummy
import kotlinx.coroutines.flow.first

class EventRepository(private val userPreference: UserPreference) {

    private suspend fun getToken(): String? {
        return userPreference.jwtToken().first()
    }

    suspend fun getEventRecommendation(): EventResponse? {
        return DataDummy.getEvent(getToken())
    }

    suspend fun getEventBySearch(searchValue: String): EventResponse? {
        return DataDummy.getEvent(getToken())
    }

    suspend fun getSavedEvent(isUpcoming: Boolean): EventResponse? {
        return DataDummy.getEvent(getToken())
    }

    suspend fun getDetailEvent(idEvent: Int): EventDetailResponse? {
        return DataDummy.getEventDetail(getToken(), idEvent)
    }

    suspend fun addEventToFavorite(idEvent: Int): BasicResponse? {
        return DataDummy.addToFavorite(getToken(), idEvent)
    }

    suspend fun removeEventFromFavorite(idEvent: Int): BasicResponse? {
        return DataDummy.removeFromFavorite(getToken(), idEvent)
    }

    suspend fun getOwnEvent(): EventResponse? {
        return DataDummy.getEvent(getToken())
    }

    suspend fun addNewEvent(event: Event): EventDetailResponse? {
        return DataDummy.addNewEvent(getToken(), event)
    }

    suspend fun updateOwnEvent(idEvent: Int, event: Event): BasicResponse? {
        return DataDummy.updateEvent(getToken(), idEvent, event)
    }

    suspend fun deleteOwnEvent(idEvent: Int): BasicResponse? {
        return DataDummy.deleteEvent(getToken(), idEvent)
    }

    companion object {
        @Volatile
        private var INSTANCE: EventRepository? = null

        fun getInstance(userPreference: UserPreference) : EventRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = EventRepository(userPreference)
                INSTANCE = instance
                instance
            }
        }
    }
}