package com.eventurecapstone.eventure.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.RegisterRequest
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val userRepository: UserRepository
): ViewModel() {
    var email = ""

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun register(fullName: String, email: String, password: String){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val field = RegisterRequest(fullName, email, password)
            val result = userRepository.register(field)
            if (result.isSuccess){
                _isSuccess.postValue(true)
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}