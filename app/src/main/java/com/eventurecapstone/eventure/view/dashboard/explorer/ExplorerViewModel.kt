package com.eventurecapstone.eventure.view.dashboard.explorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.entity.Event

class ExplorerViewModel : ViewModel() {

    private val _events = MutableLiveData<List<Event>>().apply {
        value = listOf(
            Event(
                id = 1,
                title = "Halooo",
                location = "indonesia",
                date = "20-02-2024",
                pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s"
            ),
            Event(
                id = 2,
                title = "Haiii",
                location = "indonesia",
                date = "20-02-2024",
                pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s"
            )
        )
    }

    val events: LiveData<List<Event>> get() = _events

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}