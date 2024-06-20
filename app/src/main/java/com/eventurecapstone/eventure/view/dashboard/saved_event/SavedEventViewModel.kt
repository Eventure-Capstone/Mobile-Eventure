package com.eventurecapstone.eventure.view.dashboard.saved_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.network.event.entity.Recommend
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class SavedEventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _events = MutableLiveData<List<Recommend>>()
    val events: LiveData<List<Recommend>> get() = _events

    fun fetchUpcomingEvent(){
        viewModelScope.launch {
            val voidData = listOf<Recommend>()
            val data = eventRepository.getSavedEvent(true)
            val value = data?.data?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
    }

    fun fetchPastEvent(){
        viewModelScope.launch {
            val voidData = listOf<Recommend>()
            val data = eventRepository.getSavedEvent(false)
            val value = data?.data?.filterNotNull() ?: voidData

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