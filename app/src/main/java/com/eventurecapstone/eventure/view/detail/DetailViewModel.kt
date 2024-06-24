package com.eventurecapstone.eventure.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _event = MutableLiveData<EventResult>()
    val event: LiveData<EventResult> get() = _event

    fun fetchEventById(idEvent: String){
        viewModelScope.launch {
            val data = eventRepository.getDetailEvent(idEvent)
            data?.data?.let { nonNullEvent ->
                _event.postValue(nonNullEvent)
                _eventIsSaved.postValue(nonNullEvent.favorite ?: false)
            }
        }
    }

    private val _eventIsSaved = MutableLiveData<Boolean>()
    val eventIsSaved: LiveData<Boolean> get() = _eventIsSaved

    fun saveEvent(){
        viewModelScope.launch {
            val idEvent = _event.value?.id ?: "0"
            val data = eventRepository.addEventToFavorite(idEvent)
            if (data?.success == true){
                _eventIsSaved.postValue(true)
            }
        }
    }

    fun unSaveEvent(){
        viewModelScope.launch {
            val idEvent = _event.value?.id ?: "0"
            val data = eventRepository.removeEventFromFavorite(idEvent)
            if (data?.success == true){
                _eventIsSaved.postValue(false)
            }
        }
    }

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()
}