package com.eventurecapstone.eventure.view.choose_interest.choose_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.data.entity.Category
import com.eventurecapstone.eventure.data.repository.PreferenceRepository

class ChooseCategoryViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {

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