package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Login
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.helper.DataDummy
import okhttp3.MultipartBody

class UserRepository {

    suspend fun login(login: Login): LoginResponse? {
        return DataDummy.login(login)
    }

    suspend fun register(register: Register): BasicResponse? {
        return DataDummy.register(register)
    }

    suspend fun updateProfile(profile: Profile, photo: MultipartBody.Part? = null): BasicResponse? {
        return DataDummy.updateProfile(profile, photo)
    }

    suspend fun updatePassword(password: String): BasicResponse? {
        return DataDummy.updatePassword(password)
    }

    suspend fun verifyAccount(verifyAccount: VerifyAccount): BasicResponse? {
        return DataDummy.verifyAccount(verifyAccount)
    }

    suspend fun setInterestByCategory(categories: List<String>): BasicResponse? {
        return DataDummy.setInterestByCategory(categories)
    }

    suspend fun setInterestByEvent(events: List<String>): BasicResponse? {
        return DataDummy.setInterestByEvent(events)
    }

    data class VerifyAccount(
        val isPersonal: Boolean,
        val nik: String,
        val nkk: String
    )

    data class Profile(
        val name: String,
        val email: String
    )

    data class Login(
        val email: String,
        val password: String
    )

    data class Register(
        val email: String,
        val password: String,
        val name: String
    )

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance() : UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository()
                INSTANCE = instance
                instance
            }
        }
    }

}