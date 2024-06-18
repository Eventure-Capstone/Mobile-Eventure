package com.eventurecapstone.eventure.view.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.data.pref.UserPreference
import kotlinx.coroutines.launch
import java.util.Locale
import com.eventurecapstone.eventure.data.entity.Profile
import com.eventurecapstone.eventure.data.repository.PreferenceRepository

class ProfileViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {
    private val _userInfo = MutableLiveData<Profile>()
    val userInfo: LiveData<Profile> get() = _userInfo

    fun fetchUserInfo(){
        val value = Profile(
            id = 1,
            name = "Imam Subekti",
            email = "imam@mail.com",
            pictureUrl = "https://t3.ftcdn.net/jpg/00/67/73/14/360_F_67731473_GAsdRUCBh7XEhM3X0tpzbIYDgHirJAgP.jpg",
            verified = false
        )
        _userInfo.postValue(value)
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