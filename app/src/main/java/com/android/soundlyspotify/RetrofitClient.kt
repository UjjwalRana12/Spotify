package com.android.soundlyspotify

import AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://test-mkcw.onrender.com"

    // Create an instance of the AuthInterceptor with a default token (you can update it later)

    private val authInterceptor = AuthInterceptor("default_token")



    // Lazy-initialized Retrofit instance with the OkHttpClient
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Lazy-initialized UserAPI instance
    val userAPI: UserAPI by lazy {
        retrofit.create(UserAPI::class.java)
    }

    // Function to update the access token dynamically
    fun updateAccessToken(newToken: String) {
        authInterceptor.updateToken(newToken)
    }
}

