package com.eventurecapstone.eventure.view.choose_interest.choose_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.data.entity.Category
import com.eventurecapstone.eventure.data.pref.UserPreference

class ChooseCategoryViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val _selectedCategories = MutableLiveData<Set<Category>>()
    val selectedCategories: LiveData<Set<Category>> = _selectedCategories

    init {
        _selectedCategories.value = emptySet()
    }

    fun toggleCategorySelection(category: Category) {
        val currentSet = _selectedCategories.value ?: emptySet()
        val newSet = currentSet.toMutableSet().apply {
            if (contains(category)) {
                remove(category)
            } else {
                add(category)
            }
        }
        _selectedCategories.value = newSet
    }
}