package com.eventurecapstone.eventure.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.pref.UserPreference
import kotlinx.coroutines.launch
import java.util.Locale

class SplashViewModel(private val userPreference: UserPreference): ViewModel() {
    val systemLanguage: LiveData<String?> = userPreference.language().asLiveData()
    fun setLanguage(lang: Locale){
        viewModelScope.launch {
            userPreference.setLanguage(lang)
        }
    }

    val systemTheme: LiveData<Boolean?> = userPreference.nightMode().asLiveData()
    fun setThemeToNight(state: Boolean = true){
        viewModelScope.launch {
            userPreference.setNightMode(state)
        }
    }

    fun setCoordinate(coordinate: UserPreference.Coordinate){
        viewModelScope.launch {
            userPreference.setCoordinate(coordinate)
        }
    }

    val token: LiveData<String?> = userPreference.jwtToken().asLiveData()

    private var _doneList = mutableMapOf(
        "language" to false,
        "theme" to false,
        "token" to false,
        "location" to false
    )
    val doneList: Map<String, Boolean> = _doneList

    private val _doneSignal = MutableLiveData<Boolean>()
    val doneSignal: LiveData<Boolean> get() = _doneSignal

    fun markJobAsDone(
        language: Boolean = false,
        theme: Boolean = false,
        token: Boolean = false,
        location: Boolean = false,
    ){
        if (language) _doneList["language"] = true
        if (theme) _doneList["theme"] = true
        if (token) _doneList["token"] = true
        if (location) _doneList["location"] = true
        _doneSignal.postValue(true)
    }
}