package com.eventurecapstone.eventure.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.network.user.entity.RegisterRequest
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun register(register: RegisterRequest){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val res = userRepository.register(register)
            if (res?.success == true){
                _isSuccess.postValue(true)
            } else {
                _isSuccess.postValue(false)
                _error.postValue(res?.message)
            }
            _isLoading.postValue(false)
        }
    }
}