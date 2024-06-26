package com.eventurecapstone.eventure.view.choose_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Preference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import kotlinx.coroutines.launch

class ChooseCategoryViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _selectedCategories = MutableLiveData<Set<Preference>>()
    val selectedCategories: LiveData<Set<Preference>> = _selectedCategories

    init {
        _selectedCategories.value = emptySet()
    }

    fun toggleCategorySelection(category: Preference) {
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

    private val _categoryList = MutableLiveData<List<Preference>>()
    val categoryList: LiveData<List<Preference>> get() {
        viewModelScope.launch {
            val voidData = listOf<Preference>()
            val response = eventRepository.getCategory()
            val value = response?.data?.filterNotNull() ?: voidData

            _categoryList.postValue(value)
        }
        return _categoryList
    }

    fun updateCategory(){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result =eventRepository.updateCategory(_selectedCategories.value?.toList() ?: emptyList())
            if (result.isSuccess){
                _isSuccess.postValue(true)
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}