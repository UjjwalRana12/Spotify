package com.android.soundlyspotify.applied_api

import com.android.soundlyspotify.Song
import com.android.soundlyspotify.data.DisplayResponse

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface SongApi {
    @GET("search")
    suspend fun searchSongs(@Query("query") query: String): ApiSongResponse
}

interface DisplayInterface {
    @GET("your-api-endpoint")
    suspend fun getSongs(): Response<DisplayResponse>
}


