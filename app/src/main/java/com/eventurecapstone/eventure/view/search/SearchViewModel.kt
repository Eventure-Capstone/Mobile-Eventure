package com.eventurecapstone.eventure.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _searchValue = MutableLiveData<String>()
    val searchValue: LiveData<String> get() = _searchValue

    fun setSearchValue(text: String){
        if (text.isNotBlank()){
            _searchValue.postValue(text)
        }
    }

    private val _events = MutableLiveData<List<EventResult>>()
    val events: LiveData<List<EventResult>> get() = _events

    fun fetchEventBySearch(searchText: String){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val voidData = emptyList<EventResult>()
            val result = eventRepository.getEventBySearch(searchText)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map { _events.postValue(it.data ?: voidData) }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}