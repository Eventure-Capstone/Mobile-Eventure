package com.eventurecapstone.eventure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.eventurecapstone.eventure.data.pref.UserPreference
import java.util.Locale

class PreferenceRepository(private val userPreference: UserPreference) {

    fun getSession(): LiveData<UserPreference.SessionInfo?> {
        return userPreference.getSession().asLiveData()
    }

    suspend fun saveSession(token: String) {
        userPreference.saveSession(token)
    }

    suspend fun removeSession() {
        userPreference.logout()
    }

    fun getLanguage(): LiveData<String?> {
        return userPreference.language().asLiveData()
    }

    suspend fun setLanguage(lang: Locale) {
        userPreference.setLanguage(lang)
    }

    fun getTheme(): LiveData<UserPreference.Theme?> {
        return userPreference.theme().asLiveData()
    }

    suspend fun setTheme(theme: UserPreference.Theme) {
        userPreference.setTheme(theme)
    }

    fun getLocation(): LiveData<UserPreference.Coordinate?> {
        return userPreference.coordinate().asLiveData()
    }

    suspend fun setLocation(latlng: UserPreference.Coordinate) {
        userPreference.setCoordinate(latlng)
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferenceRepository? = null

        fun getInstance(userPreference: UserPreference) : PreferenceRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferenceRepository(userPreference)
                INSTANCE = instance
                instance
            }
        }
    }

}