package com.android.soundlyspotify

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @POST("/api/user/register/email/")
    fun registerByEmail(@Body userData: EmailRegistrationRequest): Call<ApiResponse<Any?>>

    @POST("/api/user/register/phone/")
    fun registerByPhone(@Body userData: PhoneRegistrationRequest): Call<ApiResponse<Any?>>

    @POST("/api/user/verify/")
    fun verifyUser(@Body verifyData: VerificationRequest): Call<ApiResponse<Any?>>

    @POST("/api/user/login/")
    fun userLogin(@Body user: UserLoginRequest): Call<ApiResponse<Any?>>

    @POST("/api/user/verify/forgot-email/")
    fun verifyForgotEmail(@Body emailData: ForgotEmailVerificationRequest): Call<ApiResponse<Any?>>

    @POST("/api/user/verify/phone_number/")
    fun verifyForgotPhoneNumber(@Body phoneData: ForgotPhoneNumberRequest): Call<ApiResponse<Any?>>


    interface ApiService {
        @GET("https://test-mkcw.onrender.com/api/playlists")
        suspend fun getSongs(): ApiResponse<List<Song>>
    }

//data class ApiResponse<T>(
//        val success: Boolean,
//        val message: String,
//        val data: T
//    )


}
