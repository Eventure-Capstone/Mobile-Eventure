package com.eventurecapstone.eventure.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.entity.Event

class DetailViewModel(private val userPreference: UserPreference): ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun fetchEventById(idStory: Int){
        val value = Event(
            id = 1,
            title = "testing aja sih ini",
            location = "Lendah, Kulon Progo",
            latitude = -7.924970,
            longitude = 110.192390,
            startDate = "15-06-2024",
            startTime = "14.00",
            description = "lorem ipsum wae lah wkwkwk"
        )
        setEvent(value)
    }

    private fun setEvent(event: Event){
        _event.postValue(event)
    }

    val systemTheme: LiveData<Boolean?> = userPreference.nightMode().asLiveData()
}