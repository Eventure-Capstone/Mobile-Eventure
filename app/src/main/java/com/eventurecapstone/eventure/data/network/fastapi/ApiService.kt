package com.eventurecapstone.eventure.data.network.fastapi

import com.eventurecapstone.eventure.data.entity.RecommendRequest
import com.eventurecapstone.eventure.data.entity.RecommendResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/recommend")
    suspend fun getRecommendation(@Body request: RecommendRequest): Response<RecommendResponse>

}