package com.eventurecapstone.eventure.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.network.event.entity.Recommend
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

    private val _events = MutableLiveData<List<Recommend>>()
    val events: LiveData<List<Recommend>> get() = _events

    fun fetchEventBySearch(searchText: String){
        viewModelScope.launch {
            val voidData = listOf<Recommend>()
            val data = eventRepository.getEventBySearch(searchText)
            val value = data?.data?.filterNotNull() ?: voidData

            _events.postValue(value)
        }
    }
}