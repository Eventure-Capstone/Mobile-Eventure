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
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _event = MutableLiveData<EventResult>()
    val event: LiveData<EventResult> get() = _event

    fun fetchEventById(idEvent: String){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = eventRepository.getDetailEvent(idEvent)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map {response ->
                    response.data?.let { _event.postValue(it) }
                }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
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