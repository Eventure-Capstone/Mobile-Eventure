package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.entity.EventSingleResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.entity.PreferenceUpdateRequest
import com.eventurecapstone.eventure.data.entity.PreferenceUpdateResponse
import com.eventurecapstone.eventure.data.entity.RecommendRequest
import com.eventurecapstone.eventure.data.network.express.ApiService
import com.eventurecapstone.eventure.data.entity.PreferenceResponse
import com.eventurecapstone.eventure.data.entity.Preference
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.helper.DataDummy
import com.eventurecapstone.eventure.helper.DataTransform
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.first

class EventRepository(
    private val userPreference: UserPreference,
    private val userApiService: ApiService,
    private val eventApiService: com.eventurecapstone.eventure.data.network.fastapi.ApiService
) {

    private suspend fun getToken(): String? {
        val session = userPreference.getSession().first()
        return session?.token
    }

    suspend fun getEventRecommendation(request: RecommendRequest): Result<EventResponse> {
        return try {
            val result = eventApiService.getRecommendation(request)
            if (result.isSuccessful){
                val event = EventResponse(
                    success = result.body()?.success ?: false,
                    message = result.body()?.message ?: "",
                    data = DataTransform.fromRecommendListToEventResultList(result.body()?.data)
                )
                Result.success(event)
            } else {
                Result.failure(Exception("Error: ${result.code()} ${result.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun getEventBySearch(searchValue: String): Result<EventResponse> {
        val request = RecommendRequest(
            listOf("Hiburan", "Budaya"),
            "Semarang"
        )
        return try {
            val result = eventApiService.getRecommendation(request)
            if (result.isSuccessful){
                val event = EventResponse(
                    success = result.body()?.success ?: false,
                    message = result.body()?.message ?: "",
                    data = DataTransform.fromRecommendListToEventResultList(result.body()?.data)
                )
                Result.success(event)
            } else {
                Result.failure(Exception("Error: ${result.code()} ${result.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun getEventByCoordinate(latLng: LatLng, radius: Double): Result<EventResponse> {
        return try {
            val result = userApiService.getEventNearby(latLng.latitude, latLng.longitude, radius)
            if (result.isSuccessful){
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Error: ${result.code()} ${result.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun getSavedEvent(isUpcoming: Boolean): Result<EventResponse> {
        val request = RecommendRequest(
            listOf("Hiburan", "Budaya"),
            "Semarang"
        )
        return try {
            val result = eventApiService.getRecommendation(request)
            if (result.isSuccessful){
                val event = EventResponse(
                    success = result.body()?.success ?: false,
                    message = result.body()?.message ?: "",
                    data = DataTransform.fromRecommendListToEventResultList(result.body()?.data)
                )
                Result.success(event)
            } else {
                Result.failure(Exception("Error: ${result.code()} ${result.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun getDetailEvent(idEvent: String): Result<EventSingleResponse> {
        return try {
            val result = userApiService.getEventById("Bearer "+getToken(), idEvent)
            if (result.isSuccessful){
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Error: ${result.code()} ${result.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun addEventToFavorite(idEvent: String): BasicResponse? {
        return DataDummy.addToFavorite(getToken(), idEvent)
    }

    suspend fun removeEventFromFavorite(idEvent: String): BasicResponse? {
        return DataDummy.removeFromFavorite(getToken(), idEvent)
    }

    suspend fun getOwnEvent(): Result<EventResponse> {
        val request = RecommendRequest(
            listOf("Hiburan", "Budaya"),
            "Semarang"
        )
        return try {
            val response = eventApiService.getRecommendation(request)
            if (response.isSuccessful){
                val event = EventResponse(
                    success = response.body()?.success ?: false,
                    message = response.body()?.message ?: "",
                    data = DataTransform.fromRecommendListToEventResultList(response.body()?.data)
                )
                Result.success(event)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun addNewEvent(event: EventResult): EventSingleResponse? {
        return DataDummy.addNewEvent(getToken(), event)
    }

    suspend fun updateOwnEvent(idEvent: Int, event: EventResult): BasicResponse? {
        return DataDummy.updateEvent(getToken(), idEvent, event)
    }

    suspend fun deleteOwnEvent(idEvent: Int): BasicResponse? {
        return DataDummy.deleteEvent(getToken(), idEvent)
    }

    suspend fun getCategory(): PreferenceResponse? {
        return DataDummy.getCategory()
    }

    suspend fun updateCategory(categories: List<Preference>): Result<PreferenceUpdateResponse>{
        val selectedCategoryIds = categories.map { it.id }
        val categoriesRequest = PreferenceUpdateRequest(
            preferenceIds = selectedCategoryIds
        )
        return try {
            val result = userApiService.saveListCategory("Bearer "+getToken(), categoriesRequest)
            if (result.isSuccessful){
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Error: ${result.code()} ${result.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: EventRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            userApiService: ApiService,
            eventApiService: com.eventurecapstone.eventure.data.network.fastapi.ApiService
            ) : EventRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = EventRepository(userPreference, userApiService, eventApiService)
                INSTANCE = instance
                instance
            }
        }
    }
}