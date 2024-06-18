package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.helper.DataDummy
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class UserRepository(private val userPreference: UserPreference) {

    private suspend fun getToken(): String?{
        return userPreference.jwtToken().first()
    }

    suspend fun login(login: Login): LoginResponse? {
        return DataDummy.login(login)
    }

    suspend fun register(register: Register): BasicResponse? {
        return DataDummy.register(register)
    }

    suspend fun updateProfile(profile: Profile, photo: MultipartBody.Part? = null): BasicResponse? {
        return DataDummy.updateProfile(getToken(), profile, photo)
    }

    suspend fun updatePassword(password: String): BasicResponse? {
        return DataDummy.updatePassword(getToken(), password)
    }

    suspend fun verifyAccount(verifyAccount: VerifyAccount): BasicResponse? {
        return DataDummy.verifyAccount(getToken(), verifyAccount)
    }

    suspend fun setInterestByCategory(categories: List<String>): BasicResponse? {
        return DataDummy.setInterestByCategory(getToken(), categories)
    }

    suspend fun setInterestByEvent(events: List<String>): BasicResponse? {
        return DataDummy.setInterestByEvent(getToken(), events)
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

        fun getInstance(userPreference: UserPreference) : UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(userPreference)
                INSTANCE = instance
                instance
            }
        }
    }

}