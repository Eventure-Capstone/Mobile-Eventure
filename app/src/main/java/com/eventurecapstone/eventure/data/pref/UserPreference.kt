package com.eventurecapstone.eventure.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    private val gson = Gson()

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun language(): Flow<String?> = dataStore.data.map { it[LANGUAGE] }
    suspend fun setLanguage(lang: Locale) {
        dataStore.edit { it[LANGUAGE] = lang.toString() }
    }

    fun nightMode(): Flow<Boolean?> = dataStore.data.map { it[NIGHT_MODE] }
    suspend fun setNightMode(state: Boolean) {
        dataStore.edit { it[NIGHT_MODE] = state }
    }

    fun jwtToken(): Flow<String?> = dataStore.data.map { it[JWT_TOKEN] }
    suspend fun setJwtToken(token: String) {
        dataStore.edit { it[JWT_TOKEN] = token }
    }

    fun coordinate(): Flow<Coordinate?> {
        return dataStore.data.map { pref ->
            pref[COORDINATE]?.let {
                gson.fromJson(it, Coordinate::class.java)
            }
        }
    }
    suspend fun setCoordinate(coordinate: Coordinate) {
        val coordinateJson = gson.toJson(coordinate)
        dataStore.edit { preferences ->
            preferences[COORDINATE] = coordinateJson
        }
    }

    data class Coordinate(
        val latitude: Double,
        val longitude: Double
    )

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val LANGUAGE = stringPreferencesKey("language")
        private val NIGHT_MODE = booleanPreferencesKey("night_mode")
        private val JWT_TOKEN = stringPreferencesKey("jwt_token")
        private val COORDINATE = stringPreferencesKey("coordinate")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}