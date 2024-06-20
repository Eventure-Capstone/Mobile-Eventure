package com.eventurecapstone.eventure.view.create_event

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class CreateEventViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()

    private val _currentImageUri = MutableLiveData<Uri>()
    val currentImageUri: LiveData<Uri> get() = _currentImageUri
    fun setCurrentImageUri(uri: Uri) = _currentImageUri.postValue(uri)

    private val _categories = MutableLiveData<List<String>>()
    val category: LiveData<List<String>>
        get() {
        viewModelScope.launch {
            val res = eventRepository.getCategory()
            val value = res?.data?.filterNotNull() ?: listOf()
            _categories.postValue(value)
        }
        return _categories
    }

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _eventDetail = MutableLiveData<Event>()
    fun setEvent(event: Event){
        _eventDetail.postValue(event)
    }
    fun changeCoordinate(latLng: LatLng){
        val currentEvent = _eventDetail.value
        currentEvent?.latitude = latLng.latitude
        currentEvent?.longitude = latLng.longitude
        _eventDetail.postValue(currentEvent!!)
    }

    fun submitEvent(){
        viewModelScope.launch {
            val res = eventRepository.addNewEvent(_eventDetail.value!!)
            if (res?.success == true) {
                _isSuccess.postValue(true)
            }
        }
    }
}