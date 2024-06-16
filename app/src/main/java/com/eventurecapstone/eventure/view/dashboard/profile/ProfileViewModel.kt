package com.eventurecapstone.eventure.view.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.entity.Profile
import com.eventurecapstone.eventure.repository.DataStoreRepository
import kotlinx.coroutines.launch
import java.util.Locale

class ProfileViewModel(private val dataStoreRepository: DataStoreRepository) : ViewModel() {
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

    val systemLanguage: LiveData<String?> = dataStoreRepository.language().asLiveData()
    fun setLanguage(lang: Locale){
        viewModelScope.launch {
            dataStoreRepository.setLanguage(lang)
        }
    }

    val systemTheme: LiveData<Boolean?> = dataStoreRepository.nightMode().asLiveData()
    fun setThemeToNight(state: Boolean = true){
        viewModelScope.launch {
            dataStoreRepository.setNightMode(state)
        }
    }
}