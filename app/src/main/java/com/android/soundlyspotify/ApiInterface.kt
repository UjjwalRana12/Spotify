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

    @POST("/api/user/verify/forgot-email/")
    fun verifyForgotEmail(@Body emailData: ForgotEmailVerificationRequest): Call<ApiResponse>

    @POST("/api/user/verify/phone_number/")
    fun verifyForgotPhoneNumber(@Body phoneData: ForgotPhoneNumberRequest): Call<ApiResponse>

    interface SongAPI {
        @GET("https://test-mkcw.onrender.com/api/playlists")
        fun getSongs(): Call<List<Song>>
        //getSongs is a method
    }
}
