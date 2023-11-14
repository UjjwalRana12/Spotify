package com.android.soundlyspotify.applied_api

import com.android.soundlyspotify.Song
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SongApi {
    @GET("https://test-mkcw.onrender.com/")
    fun searchSong(@Query("query") query: String): Call<ApiSongResponse>
}
