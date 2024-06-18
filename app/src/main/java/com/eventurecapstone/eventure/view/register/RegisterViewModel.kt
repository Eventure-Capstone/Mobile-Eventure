package com.eventurecapstone.eventure.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.repository.PreferenceRepository

class RegisterViewModel(private val preferenceRepository: PreferenceRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


}