package com.android.soundlyspotify.applied_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private const val BASE_URL = "https://test-mkcw.onrender.com/"

    // Define the Retrofit instance
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Define the SongApi instance
    val instance: SongApi = retrofit.create(SongApi::class.java)
}
//hello world