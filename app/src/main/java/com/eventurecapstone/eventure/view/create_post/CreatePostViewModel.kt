package com.eventurecapstone.eventure.view.create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val eventRepository: EventRepository
): ViewModel() {

    private val _currentImageUri = MutableLiveData<Uri>()
    val currentImageUri: LiveData<Uri> get() = _currentImageUri
    fun setCurrentImageUri(uri: Uri) = _currentImageUri.postValue(uri)

    private val _categories = MutableLiveData<List<String>>()
    val category: LiveData<List<String>> get() {
        viewModelScope.launch {
            val res = eventRepository.getCategory()
            val value = res?.data?.filterNotNull() ?: listOf()
            _categories.postValue(value)
        }
        return _categories
    }

}