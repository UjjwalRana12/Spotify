package com.android.soundlyspotify.applied_api

import AuthInterceptor
import com.android.soundlyspotify.data.SongApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetroClient {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"

    // Create an instance of the AuthInterceptor with a default token (you can update it later)
    private val authInterceptor = AuthInterceptor("default_token")

    // Create an OkHttpClient with the AuthInterceptor
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .build()

    // Define the Retrofit instance
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    // Define the SongApi instance
    val instance: SongApi = retrofit.create(SongApi::class.java)

    // Function to update the access token dynamically
    fun updateAccessToken(newToken: String) {
        authInterceptor.updateToken(newToken)
    }
}
