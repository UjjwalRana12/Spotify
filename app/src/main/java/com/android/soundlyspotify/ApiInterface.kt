package com.android.soundlyspotify

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @POST("/api/user/register/email/")
    fun registerByEmail(@Body userData: EmailRegistrationRequest): Call<ApiResponse>

    @POST("/api/user/register/phone/")
    fun registerByPhone(@Body userData: PhoneRegistrationRequest): Call<ApiResponse>

    @POST("/api/user/verify/")
    fun verifyUser(@Body verifyData: VerificationRequest): Call<ApiResponse>

    @POST("/api/user/login/")
    fun userLogin(@Body user: UserLoginRequest): Call<ApiResponse>

    @POST("/api/user/forgot-email/")
    fun verifyForgotEmail(@Body emailData: ForgotEmailVerificationRequest): Call<ApiResponse>

    @POST("/api/user/forgot-phone_number/")
    fun verifyForgotPhoneNumber(@Body phoneData: ForgotPhoneNumberRequest): Call<ApiResponse>
}

    interface ApiService {
        @GET("https://test-mkcw.onrender.com/api/playlists")
        suspend fun getSongs(): ApikaResponse<List<Song>>
    }

//data class ApiResponse<T>(
//        val success: Boolean,
//        val message: String,
//        val data: T
//    )



