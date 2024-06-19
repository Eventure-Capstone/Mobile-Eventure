package com.eventurecapstone.eventure.view.email_verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    fun verify(code: String){
        viewModelScope.launch {
            val res = userRepository.verifyEmail(code)
            if (res?.success == true){
                _isLoginSuccess.postValue(true)
            } else {
                _isLoginSuccess.postValue(false)
            }
        }
    }
}