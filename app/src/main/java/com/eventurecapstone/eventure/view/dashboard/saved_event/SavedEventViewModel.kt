package com.eventurecapstone.eventure.view.dashboard.saved_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class SavedEventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    fun fetchUpcomingEvent(){
        viewModelScope.launch {
            val voidData = listOf<Event>()
            val data = eventRepository.getSavedEvent(true)
            val value = data?.event?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
    }

    fun fetchPastEvent(){
        viewModelScope.launch {
            val voidData = listOf<Event>()
            val data = eventRepository.getSavedEvent(false)
            val value = data?.event?.filterNotNull() ?: voidData

            _events.postValue(value)
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