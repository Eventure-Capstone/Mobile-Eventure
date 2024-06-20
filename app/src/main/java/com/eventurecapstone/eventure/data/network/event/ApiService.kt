package com.eventurecapstone.eventure.data.network.event

import com.eventurecapstone.eventure.data.network.event.entity.RecommendRequest
import com.eventurecapstone.eventure.data.network.event.entity.RecommendResponse
import com.eventurecapstone.eventure.data.network.user.entity.CategoryResponse
import com.eventurecapstone.eventure.data.network.user.entity.LoginRequest
import com.eventurecapstone.eventure.data.network.user.entity.LoginResponse
import com.eventurecapstone.eventure.data.network.user.entity.RegisterRequest
import com.eventurecapstone.eventure.data.network.user.entity.RegisterResponse
import com.eventurecapstone.eventure.data.network.user.entity.UserResponse
import com.eventurecapstone.eventure.data.network.user.entity.VerifyRequest
import com.eventurecapstone.eventure.data.network.user.entity.VerifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/recommend")
    suspend fun getRecommendation(@Body request: RecommendRequest): Response<RecommendResponse>

}