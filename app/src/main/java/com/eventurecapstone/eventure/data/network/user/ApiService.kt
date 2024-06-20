package com.eventurecapstone.eventure.data.network.user

import com.eventurecapstone.eventure.data.network.user.entity.LoginRequest
import com.eventurecapstone.eventure.data.network.user.entity.LoginResponse
import com.eventurecapstone.eventure.data.network.user.entity.RegisterRequest
import com.eventurecapstone.eventure.data.network.user.entity.RegisterResponse
import com.eventurecapstone.eventure.data.network.user.entity.VerifyRequest
import com.eventurecapstone.eventure.data.network.user.entity.VerifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/api/v1/verify")
    suspend fun verify(@Body request: VerifyRequest): Response<VerifyResponse>
}