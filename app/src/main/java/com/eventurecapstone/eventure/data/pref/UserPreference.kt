package com.eventurecapstone.eventure.data.pref

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.eventurecapstone.eventure.helper.JwtHelper
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    private val gson = Gson()

    fun getSession(): Flow<SessionInfo?> {
        return dataStore.data.map { preferences ->
            SessionInfo(
                idUser = preferences[USER_ID],
                token = preferences[JWT_TOKEN]
            )
        }
    }

    suspend fun saveSession(token: String) {
        val idUser: String = JwtHelper.decodeJwtToken(token, "id")!!
        Log.i("TAG", "saveSession: $idUser")
        dataStore.edit {
            it[JWT_TOKEN] = token
            it[USER_ID] = idUser
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(JWT_TOKEN)
            preferences.remove(USER_ID)
        }
    }

    fun language(): Flow<String?> = dataStore.data.map { it[LANGUAGE] }
    suspend fun setLanguage(lang: Locale) {
        dataStore.edit { it[LANGUAGE] = lang.toString() }
    }

    fun theme(): Flow<Theme?> = dataStore.data.map { if(it[THEME] == true) Theme.NIGHT else Theme.LIGHT }
    suspend fun setTheme(state: Theme) {
        val isNightMode = state == Theme.NIGHT
        dataStore.edit { it[THEME] = isNightMode }
    }

    fun coordinate(): Flow<Coordinate?> {
        return dataStore.data.map { preference ->
            preference[COORDINATE]?.let {
                gson.fromJson(it, Coordinate::class.java)
            }
        }
    }
    suspend fun setCoordinate(coordinate: Coordinate) {
        val coordinateJson = gson.toJson(coordinate)
        dataStore.edit {
            it[COORDINATE] = coordinateJson
        }
    }

    data class Coordinate(
        val latitude: Double,
        val longitude: Double
    )

    data class SessionInfo(
        val idUser: String?,
        val token: String?
    )

    data class User(
        val name: String,
        val email: String,
        val id: Int,
        val pictureUrl: String?,
        val verified: Boolean
    )

    enum class Theme {
        NIGHT,
        LIGHT
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val LANGUAGE = stringPreferencesKey("language")
        private val THEME = booleanPreferencesKey("night_mode")
        private val JWT_TOKEN = stringPreferencesKey("jwt_token")
        private val COORDINATE = stringPreferencesKey("coordinate")
        private val USER_ID = stringPreferencesKey("user")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}