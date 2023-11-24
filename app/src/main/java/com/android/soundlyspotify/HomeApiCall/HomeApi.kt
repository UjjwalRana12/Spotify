package com.android.soundlyspotify.HomeApiCall

import retrofit2.Call
import retrofit2.http.GET

interface HomeApi {
    @GET("foryou/")
    fun getForYouSongs(): Call<Apiforyou>
}
