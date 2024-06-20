package com.eventurecapstone.eventure.view.choose_interest.choose_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.network.user.entity.Category
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import kotlinx.coroutines.launch

class ChooseCategoryViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

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

    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> get() {
        viewModelScope.launch {
            val voidData = listOf<Category>()
            val response = eventRepository.getCategory()
            val value = response?.data?.filterNotNull() ?: voidData

            _categoryList.postValue(value)
        }
        return _categoryList
    }
}