package com.eventurecapstone.eventure.view.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.entity.Profile

class ProfileViewModel : ViewModel() {
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
}