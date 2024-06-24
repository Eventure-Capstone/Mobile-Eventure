package com.eventurecapstone.eventure.view.email_verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.OtpRequest
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    var email = ""

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun verify(code: String){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = userRepository.verifyEmail(OtpRequest(email, code))
            if (result.isSuccess){
                _isSuccess.postValue(true)
            } else {
                _isSuccess.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}