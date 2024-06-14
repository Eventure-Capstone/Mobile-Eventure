package com.eventurecapstone.eventure.view.dashboard.saved_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.entity.Event

class SavedEventViewModel : ViewModel() {

    private val _events = MutableLiveData<List<Event>>().apply {
        value = listOf(
            Event(
                id = 1,
                title = "Halooo",
                location = "indonesia",
                startDate = "20-02-2024",
                latitude = -7.924970,
                longitude = 110.192390,
                pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s"
            ),
            Event(
                id = 2,
                title = "Haiii",
                location = "indonesia",
                startDate = "20-02-2024",
                latitude = -7.924970,
                longitude = 110.192390,
                pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s"
            )
        )
    }

    val events: LiveData<List<Event>> get() = _events

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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