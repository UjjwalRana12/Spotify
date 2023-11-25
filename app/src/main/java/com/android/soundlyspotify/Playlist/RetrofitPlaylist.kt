package com.android.soundlyspotify.Playlist

import com.android.soundlyspotify.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitPlaylist {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"

    // Create an instance of the AuthInterceptor with a default token (you can update it later)
    private val authInterceptor = AuthInterceptor("default_token")

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Set the OkHttpClient with the AuthInterceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val playlistService: PlaylistService by lazy {
        retrofit.create(PlaylistService::class.java)
    }

    // Function to update the access token dynamically
    fun updateAccessToken(newToken: String) {
        authInterceptor.updateToken(newToken)
    }
}
