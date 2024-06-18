package com.eventurecapstone.eventure.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.entity.Login
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.pref.UserModel
import com.eventurecapstone.eventure.data.pref.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val userPreference: UserPreference): ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val email = "email@email.com"
    private val password = "123456"

    fun login(email: String, password: String): Boolean {
        if (email == this.email && password == this.password){
            _isLoading.value = false
            val loginData = Login(
                    "Ichsan Ardika",
                    "1",
                    "12345"
                    )
            val loginRes = LoginResponse(
                loginData,
                false,
                "Login Success"
            )
            _loginResponse.postValue(loginRes)
            return true
        }
        else {
            _isLoading.value = false
            return false
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userPreference.saveSession(user)
        }
    }


}