package com.eventurecapstone.eventure.view.dashboard.explorer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.pref.UserPreference
import kotlinx.coroutines.launch
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.entity.RecommendRequest
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository

class ExplorerViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isHavePreference = MutableLiveData<Boolean>()
    val isHavePreference: LiveData<Boolean> = _isHavePreference

    fun checkPreference(){
        if (_isHavePreference.value != null){
            return
        }

        viewModelScope.launch {
            val result = userRepository.getProfile()
            result.map {
                val prefNotNull = it.data?.preferences != null && it.data.preferences.isNotEmpty()
                _isHavePreference.postValue(prefNotNull)
            }
        }
    }

    private val _events = MutableLiveData<List<EventResult>>()
    val events: LiveData<List<EventResult>> get() = _events

    fun fetchEvent(){
        _isLoading.postValue(true)
        val req = RecommendRequest(
            category = listOf("Hiburan", "Budaya"),
            locationCity = "Semarang"
        )
        viewModelScope.launch {
            val voidData = emptyList<EventResult>()
            val result = eventRepository.getEventRecommendation(req)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map { _events.postValue(it.data ?: voidData) }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }

    val coordinate: LiveData<UserPreference.Coordinate?> = preferenceRepository.getLocation()
    fun setCoordinate(coordinate: UserPreference.Coordinate){
        viewModelScope.launch {
            preferenceRepository.setLocation(coordinate)
        }
    }

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()
}