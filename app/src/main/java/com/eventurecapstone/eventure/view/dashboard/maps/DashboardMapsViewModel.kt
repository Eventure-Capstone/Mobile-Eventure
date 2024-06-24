package com.eventurecapstone.eventure.view.dashboard.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class DashboardMapsViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    val coordinate: LiveData<UserPreference.Coordinate?> = preferenceRepository.getLocation()
    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()

    private val _events = MutableLiveData<List<EventResult>>()
    val events: LiveData<List<EventResult>> get() = _events

    fun getEvent(latLng: LatLng){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val voidData = emptyList<EventResult>()
            val result = eventRepository.getEventByCoordinate(latLng, 10.0)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map { _events.postValue(it.data ?: voidData) }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }

    fun clearEvent(){
        _events.postValue(listOf())
    }
}