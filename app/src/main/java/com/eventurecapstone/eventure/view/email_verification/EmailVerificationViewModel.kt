package com.eventurecapstone.eventure.view.email_verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.network.user.entity.VerifyRequest
import com.eventurecapstone.eventure.data.repository.UserRepository
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    var email = ""

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun verify(code: String){
        viewModelScope.launch {
            val res = userRepository.verifyEmail(VerifyRequest(
                email = email,
                otp_code = code
            ))
            if (res?.data != null){
                _isSuccess.postValue(true)
            } else {
                _isSuccess.postValue(false)
            }
        }
    }
}