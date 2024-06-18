package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.LoginRegisterRequest
import com.eventurecapstone.eventure.data.entity.LoginResponse

class UserRepository {

    fun login(loginRegisterRequest: LoginRegisterRequest): LoginResponse? {
        return null
    }

    fun register(loginRegisterRequest: LoginRegisterRequest): BasicResponse? {
        return null
    }

    fun updateProfile() {}

    fun updatePassword() {}

    fun updatePhoto() {}

    fun verifyAccount() {}

    fun setInterestByCategory() {}

    fun setInterestByEvent() {}

}