package com.eventurecapstone.eventure.view.choose_interest.choose_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.pref.UserPreference

class ChooseEventViewModel(private val userPreference: UserPreference): ViewModel() {
    private val _selectedItems = MutableLiveData<MutableList<Event>>().apply { value = mutableListOf() }
    val selectedItems: LiveData<MutableList<Event>> = _selectedItems

    fun toggleSelection(item: Event) {
        val currentList = _selectedItems.value ?: mutableListOf()
        if (currentList.contains(item)) {
            currentList.remove(item)
        } else {
            currentList.add(item)
        }
        _selectedItems.value = currentList
    }
}