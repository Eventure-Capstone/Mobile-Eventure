package com.eventurecapstone.eventure.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun fetchEventById(idStory: Int){
        viewModelScope.launch {
            val data = eventRepository.getDetailEvent(idStory)
            data?.event?.let { nonNullEvent ->
                _event.postValue(nonNullEvent)
            }
        }
    }

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()
}