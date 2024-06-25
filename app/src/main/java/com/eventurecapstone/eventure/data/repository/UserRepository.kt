package com.eventurecapstone.eventure.data.repository

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.LoginRequest
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.entity.OtpRequest
import com.eventurecapstone.eventure.data.entity.OtpResponse
import com.eventurecapstone.eventure.data.entity.RegisterRequest
import com.eventurecapstone.eventure.data.entity.UserResponse
import com.eventurecapstone.eventure.data.network.express.ApiService
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

    suspend fun login(login: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(login)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun register(register: RegisterRequest): Result<UserResponse> {
        return try {
            val response = apiService.register(register)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun verifyEmail(verify: OtpRequest): Result<OtpResponse> {
        return try {
            val response = apiService.verify(verify)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun getProfile(): Result<UserResponse> {
        return try {
            val response = apiService.getProfile(("Bearer " + getToken()))
            if (response.isSuccessful){
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun updateProfile(photo: MultipartBody.Part): Result<UserResponse> {
        return try {
            val response = apiService.uploadProfilePicture("Bearer " + getToken(), photo)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error: ${e.message}"))
        }
    }

    suspend fun updatePassword(password: String): BasicResponse? {
        return DataDummy.updatePassword(getToken(), password)
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