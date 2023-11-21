package com.android.soundlyspotify.applied_api
import com.android.soundlyspotify.data.SongApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetroClient {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"

    // Define the Retrofit instance
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build()) // Adjust timeout as needed
        .build()

    // Define the SongApi instance
    val instance: SongApi = retrofit.create(SongApi::class.java)



}
//hello world