package com.eventurecapstone.eventure.view.my_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.network.event.entity.Recommend
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class MyPostViewModel(
    private val eventRepository: EventRepository
): ViewModel() {

    private val _events = MutableLiveData<List<Recommend>>()

    val events: LiveData<List<Recommend>> get() {
        viewModelScope.launch {
            val voidData = listOf<Recommend>()
            val data = eventRepository.getOwnEvent()
            val value = data?.data?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
        return _events
    }

}