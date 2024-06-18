package com.eventurecapstone.eventure.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _searchValue = MutableLiveData<String>()
    val searchValue: LiveData<String> get() = _searchValue

    fun setSearchValue(text: String){
        if (text.isNotBlank()){
            _searchValue.postValue(text)
        }
    }

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    fun fetchEventBySearch(searchText: String){
        viewModelScope.launch {
            val voidData = listOf<Event>()
            val data = eventRepository.getEventBySearch(searchText)
            val value = data?.event?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
    }
}