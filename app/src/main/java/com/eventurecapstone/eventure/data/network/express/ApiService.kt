package com.eventurecapstone.eventure.data.network.express

import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.entity.EventSingleResponse
import com.eventurecapstone.eventure.data.entity.LoginRequest
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.entity.OtpRequest
import com.eventurecapstone.eventure.data.entity.OtpResponse
import com.eventurecapstone.eventure.data.entity.PreferenceUpdateRequest
import com.eventurecapstone.eventure.data.entity.PreferenceUpdateResponse
import com.eventurecapstone.eventure.data.entity.RegisterRequest
import com.eventurecapstone.eventure.data.entity.UserResponse
import com.eventurecapstone.eventure.data.entity.PreferenceResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/api/v1/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/register")
    suspend fun register(@Body request: RegisterRequest): Response<UserResponse>

    @POST("/api/v1/verify")
    suspend fun verify(@Body request: OtpRequest): Response<OtpResponse>

    @GET("/api/v1/users/me")
    suspend fun getProfile(@Header("Authorization") authorization: String): Response<UserResponse>

    @GET("api/v1/preferences")
    suspend fun getListCategory(): Response<PreferenceResponse>

    @POST("api/v1/preferences")
    suspend fun saveListCategory(
        @Header("Authorization") authorization: String,
        @Body categories: PreferenceUpdateRequest
    ): Response<PreferenceUpdateResponse>

    @Multipart
    @POST("api/v1/users/profile_img")
    suspend fun uploadProfilePicture(
        @Header("Authorization") authorization: String,
        @Part("profile_Img") profileImg: MultipartBody.Part
    ): Response<UserResponse>

    @GET("api/v1/events/nearby")
    suspend fun getEventNearby(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Double,
    ): Response<EventResponse>

    @GET("api/v1/events/{id}")
    suspend fun getEventById(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ) : Response<EventSingleResponse>
}