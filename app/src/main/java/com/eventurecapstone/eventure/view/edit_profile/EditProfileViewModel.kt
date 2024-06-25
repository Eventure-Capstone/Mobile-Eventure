package com.eventurecapstone.eventure.view.edit_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditProfileViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val userRepository: UserRepository
    ): ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    private val _updateProfileResponse = MutableLiveData<BasicResponse?>()
    val updateProfileResponse: LiveData<BasicResponse?> get() = _updateProfileResponse

    fun updateImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setProfile(photo: MultipartBody.Part? = null) {
        viewModelScope.launch {
//            val response = userRepository.updateProfile(photo!!)
//            _updateProfileResponse.value = response
        }
    }
}