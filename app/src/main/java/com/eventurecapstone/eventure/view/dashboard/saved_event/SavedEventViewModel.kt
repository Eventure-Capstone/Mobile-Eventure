package com.eventurecapstone.eventure.view.dashboard.saved_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class SavedEventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _events = MutableLiveData<List<EventResult>>()
    val events: LiveData<List<EventResult>> get() = _events

    fun fetchUpcomingEvent(){
        _isLoading.postValue(true)
        _events.postValue(emptyList())
        viewModelScope.launch {
            val voidData = emptyList<EventResult>()
            val result = eventRepository.getSavedEvent(true)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map { _events.postValue(it.data ?: voidData) }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }

    fun fetchPastEvent(){
        _isLoading.postValue(true)
        _events.postValue(emptyList())
        viewModelScope.launch {
            val voidData = emptyList<EventResult>()
            val result = eventRepository.getSavedEvent(false)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map { _events.postValue(it.data ?: voidData) }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }

    private val _eventStatus = MutableLiveData(ButtonState.UPCOMING)
    val eventStatus: LiveData<ButtonState> get() = _eventStatus

    fun setEventStatus(eventIsDone: ButtonState){
        _eventStatus.postValue(eventIsDone)
    }

    enum class ButtonState {
        UPCOMING,
        PAST
    }
}