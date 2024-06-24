package com.eventurecapstone.eventure.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import kotlinx.coroutines.launch
import java.util.Locale

class SplashViewModel(private val preferenceRepository: PreferenceRepository): ViewModel() {
    val systemLanguage: LiveData<String?> = preferenceRepository.getLanguage()
    fun setLanguage(lang: Locale){
        viewModelScope.launch {
            preferenceRepository.setLanguage(lang)
        }
    }

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()
    fun setTheme(state: UserPreference.Theme){
        viewModelScope.launch {
            preferenceRepository.setTheme(state)
        }
    }

    fun setCoordinate(coordinate: UserPreference.Coordinate){
        viewModelScope.launch {
            preferenceRepository.setLocation(coordinate)
        }
    }

    val user: LiveData<UserPreference.SessionInfo?> = preferenceRepository.getSession()

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