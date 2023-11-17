package com.android.soundlyspotify.Playlist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitPlaylist {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val playlistApi: PlaylistApi by lazy {
        retrofit.create(PlaylistApi::class.java)
    }
}
