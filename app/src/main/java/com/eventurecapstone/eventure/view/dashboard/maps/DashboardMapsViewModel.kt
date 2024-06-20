package com.eventurecapstone.eventure.view.dashboard.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.network.event.entity.Recommend
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class DashboardMapsViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _events = MutableLiveData<List<Recommend>>()
    val events: LiveData<List<Recommend>> get() = _events

    val coordinate: LiveData<UserPreference.Coordinate?> = preferenceRepository.getLocation()

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()

    fun getEvent(latLng: LatLng){
        viewModelScope.launch {
            val voidData = listOf<Recommend>()
            val data = eventRepository.getEventByCoordinate(latLng, 10.0)
            val value = data?.data?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
    }

    fun clearEvent(){
        _events.postValue(listOf())
    }
}