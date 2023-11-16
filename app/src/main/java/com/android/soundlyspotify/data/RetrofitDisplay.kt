package com.android.soundlyspotify.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDisplay {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"

    // Define the OkHttpClient instance with a timeout
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    // Define the Retrofit instance with GsonConverterFactory and OkHttpClient
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    // Define the DisplayInterface instance
    val instance: DisplayInterface = retrofit.create(DisplayInterface::class.java)

    // Define the SongApiService instance
    val songApiService: SongApiService = retrofit.create(SongApiService::class.java)
}
