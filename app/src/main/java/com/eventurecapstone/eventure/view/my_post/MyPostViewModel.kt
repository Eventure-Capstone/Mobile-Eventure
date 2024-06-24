package com.eventurecapstone.eventure.view.my_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.repository.EventRepository
import kotlinx.coroutines.launch

class MyPostViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _events = MutableLiveData<List<EventResult>>()

    val events: LiveData<List<EventResult>> get() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = eventRepository.getOwnEvent()
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map { _events.postValue(it.data!!) }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
        return _events
    }

}