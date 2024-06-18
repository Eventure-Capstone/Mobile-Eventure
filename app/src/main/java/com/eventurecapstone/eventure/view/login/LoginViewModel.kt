package com.eventurecapstone.eventure.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    fun login(email: String, password: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val field = UserRepository.Login(email, password)
            val response = userRepository.login(field)
            if (response?.success == true){
                _isLoginSuccess.postValue(true)
                preferenceRepository.saveSession(response.loginResult!!)
            } else {
                _isLoginSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}