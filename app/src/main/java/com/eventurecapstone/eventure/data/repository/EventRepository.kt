package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.entity.EventDetailResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.network.user.ApiService
import com.eventurecapstone.eventure.data.network.user.entity.CategoryResponse
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.helper.DataDummy
import kotlinx.coroutines.flow.first

class EventRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    private suspend fun getToken(): String? {
        val session = userPreference.getSession().first()
        return session?.token
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

    suspend fun getCategory(): CategoryResponse? {
        val response = apiService.getListCategory()
        return if (response.isSuccessful) response.body() else null
    }

    companion object {
        @Volatile
        private var INSTANCE: EventRepository? = null

        fun getInstance(userPreference: UserPreference, apiService: ApiService) : EventRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = EventRepository(userPreference, apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}