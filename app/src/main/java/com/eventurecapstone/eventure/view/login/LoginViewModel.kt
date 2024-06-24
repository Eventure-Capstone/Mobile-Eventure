package com.eventurecapstone.eventure.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.LoginRequest
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun login(email: String, password: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val field = LoginRequest(email, password)
            val result = userRepository.login(field)
            if (result.isSuccess){
                _isSuccess.postValue(true)
                result.map {response ->
                    response.data?.let { preferenceRepository.saveSession(it.token) }
                }
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}