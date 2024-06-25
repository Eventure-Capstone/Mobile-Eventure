package com.eventurecapstone.eventure.view.create_event

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.entity.Preference
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.helper.ImageUploader
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class CreateEventViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()
    val lastLocation: LiveData<UserPreference.Coordinate?> = preferenceRepository.getLocation()

    private val _currentImageUri = MutableLiveData<Uri>()
    val currentImageUri: LiveData<Uri> get() = _currentImageUri
    fun setCurrentImageUri(uri: Uri) = _currentImageUri.postValue(uri)

    private val _bannerUrl = MutableLiveData<String>()

    private val _categories = MutableLiveData<List<Preference>>()
    val category: LiveData<List<Preference>>
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _eventDetail = MutableLiveData<EventResult>()
    fun setEvent(event: EventResult){
        _eventDetail.postValue(event)
    }

    fun changeCoordinate(latLng: LatLng){
        val currentEvent = _eventDetail.value
        currentEvent?.latitude = latLng.latitude
        currentEvent?.longitude = latLng.longitude
        _eventDetail.postValue(currentEvent!!)
    }

    fun submitEvent(context: Context){
        _isLoading.postValue(true)
        if (_currentImageUri.value != null){
            uploadBanner(context)
        }
        _bannerUrl.observeForever {
            viewModelScope.launch {
                if (!it.isNullOrBlank()){
                    val oldEvent = _eventDetail.value
                    val event = EventResult(
                        title = oldEvent?.title,
                        category = oldEvent?.category,
                        description = oldEvent?.description,
                        banner = _bannerUrl.value,
                        city = oldEvent?.city,
                        fullAddress = oldEvent?.fullAddress,
                        date = oldEvent?.date,
                        latitude = oldEvent?.latitude,
                        longitude = oldEvent?.longitude,
                    )
                    Log.i("TAG", "submitEvent: $event")
//                    val result = eventRepository.addNewEvent(event)
//                    if (result.isSuccess){
//                        _isSuccess.postValue(true)
//                    } else {
//                        _isSuccess.postValue(false)
//                    }
                }
            }
        }
    }

    private fun uploadBanner(context: Context){
        viewModelScope.launch {
            val photo = ImageUploader.getRequestBodyFromUri(_currentImageUri.value!!, context, "banner")
            val uploadBanner = eventRepository.uploadBannerEvent(photo!!)
            if (uploadBanner.isSuccess){
                uploadBanner.map {
                    _bannerUrl.postValue(it.data?.bannerUrl ?: "")
                }
            } else {
                _isSuccess.postValue(false)
            }
        }
    }
}