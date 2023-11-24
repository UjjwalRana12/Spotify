package com.android.soundlyspotify.HomeApiCall

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeClient {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"

    val homeApi: HomeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }
}
