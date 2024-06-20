package com.eventurecapstone.eventure.view.dashboard.explorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.pref.UserPreference
import kotlinx.coroutines.launch
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.network.event.entity.Recommend
import com.eventurecapstone.eventure.data.network.event.entity.RecommendRequest
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.helper.DataDummy

class ExplorerViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _events = MutableLiveData<List<Recommend>>()
    val events: LiveData<List<Recommend>> get() = _events

    fun fetchEvent(){
        val req = RecommendRequest(
            category = listOf("Hiburan", "Budaya"),
            locationCity = "Semarang"
        )
        viewModelScope.launch {
            val voidData = listOf<Recommend>()
            val data = eventRepository.getEventRecommendation(req)
            val value = data?.data?.filterNotNull() ?: voidData

            _events.postValue(value)
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