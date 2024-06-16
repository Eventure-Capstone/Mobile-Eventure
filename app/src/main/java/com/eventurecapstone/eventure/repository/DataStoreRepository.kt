package com.eventurecapstone.eventure.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreRepository private constructor(private val dataStore: DataStore<Preferences>){

    fun language(): Flow<String?> {
        return dataStore.data.map { it[LANGUAGE] }
    }

    suspend fun setLanguage(lang: Locale) {
        dataStore.edit { it[LANGUAGE] = lang.toString() }
    }

    fun nightMode(): Flow<Boolean?> {
        return dataStore.data.map { it[NIGHT_MODE] }
    }

    suspend fun setNightMode(state: Boolean) {
        dataStore.edit { it[NIGHT_MODE] = state }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataStoreRepository? = null

        private val LANGUAGE = stringPreferencesKey("language")
        private val NIGHT_MODE = booleanPreferencesKey("night_mode")

        fun getInstance(dataStore: DataStore<Preferences>): DataStoreRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreRepository(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}