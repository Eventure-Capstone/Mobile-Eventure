package com.eventurecapstone.eventure.view.my_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class MyPostViewModel(
    private val eventRepository: EventRepository
): ViewModel() {

    private val _events = MutableLiveData<List<Event>>()

    val events: LiveData<List<Event>> get() {
        viewModelScope.launch {
            val voidData = listOf<Event>()
            val data = eventRepository.getOwnEvent()
            val value = data?.event?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
        return _events
    }

}