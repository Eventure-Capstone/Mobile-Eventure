package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.network.user.ApiService
import com.eventurecapstone.eventure.data.network.user.entity.LoginRequest
import com.eventurecapstone.eventure.data.network.user.entity.LoginResponse
import com.eventurecapstone.eventure.data.network.user.entity.RegisterRequest
import com.eventurecapstone.eventure.data.network.user.entity.RegisterResponse
import com.eventurecapstone.eventure.data.network.user.entity.UserResponse
import com.eventurecapstone.eventure.data.network.user.entity.VerifyRequest
import com.eventurecapstone.eventure.data.network.user.entity.VerifyResponse
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.helper.DataDummy
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    private suspend fun getToken(): String?{
        val session = userPreference.getSession().first()
        return session?.token
    }

    suspend fun login(login: LoginRequest): LoginResponse? {
        val response = apiService.login(login)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun register(register: RegisterRequest): RegisterResponse? {
        val response = apiService.register(register)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun verifyEmail(verify: VerifyRequest): VerifyResponse? {
        val response = apiService.verify(verify)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getProfile(): UserResponse? {
        val response = apiService.getProfile(("Bearer " + getToken()))
        return if (response.isSuccessful) response.body() else null
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

        fun getInstance(userPreference: UserPreference, apiService: ApiService) : UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(userPreference, apiService)
                INSTANCE = instance
                instance
            }
        }
    }

}