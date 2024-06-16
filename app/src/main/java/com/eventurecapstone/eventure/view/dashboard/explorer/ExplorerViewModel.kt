package com.eventurecapstone.eventure.view.dashboard.explorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.data.entity.Event

class ExplorerViewModel : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    fun fetchEvent(){
        val value = listOf(
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
        _events.postValue(value)
    }
}