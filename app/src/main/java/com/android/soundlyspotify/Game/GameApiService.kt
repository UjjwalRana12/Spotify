package com.android.soundlyspotify

import com.android.soundlyspotify.GameData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GameApiService {
    @GET("music-clips/")
    fun getGameData(): Call<GameData>

    @POST("your/post/endpoint")
    fun postSelectedOption(@Body postData: Map<String, String>): Call<GameResponse>
}

