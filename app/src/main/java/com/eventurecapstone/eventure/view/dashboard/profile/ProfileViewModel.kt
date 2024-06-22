package com.eventurecapstone.eventure.view.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.pref.UserPreference
import kotlinx.coroutines.launch
import java.util.Locale
import com.eventurecapstone.eventure.data.entity.Profile
import com.eventurecapstone.eventure.data.network.user.entity.UserResponse
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository

class ProfileViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userInfo = preferenceRepository.getSession()
    val userInfo: LiveData<UserPreference.SessionInfo?> get() = _userInfo

    private val _users = MutableLiveData<UserResponse?>()
    val users: LiveData<UserResponse?> get() {
        viewModelScope.launch {
            val res = userRepository.getProfile()
            if (res != null) {
                _users.postValue(res)
            }
        }
        return _users
    }

    fun logout(){
        viewModelScope.launch {
            preferenceRepository.removeSession()
        }
    }

    val systemLanguage: LiveData<String?> = preferenceRepository.getLanguage()
    fun setLanguage(lang: Locale){
        viewModelScope.launch {
            preferenceRepository.setLanguage(lang)
        }
    }

    val systemTheme: LiveData<UserPreference.Theme?> = preferenceRepository.getTheme()
    fun setTheme(state: UserPreference.Theme){
        viewModelScope.launch {
            preferenceRepository.setTheme(state)
        }
    }
}