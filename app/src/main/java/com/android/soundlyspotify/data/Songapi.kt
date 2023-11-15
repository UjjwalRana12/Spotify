package com.android.soundlyspotify.data

import com.android.soundlyspotify.applied_api.ApiSongResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface SongApi {
    @GET("search")
    suspend fun searchSongs(@Query("query") query: String): ApiSongResponse
}

interface DisplayInterface {
    @GET("allpublicsongs")
    suspend fun getOfferSongs(): Response<DisplayResponse>

    @GET("allpublicsongs")
    suspend fun getBestsellerSongs(): Response<DisplayResponse>

    @GET("allpublicsongs")
    suspend fun getClothingSongs(): Response<DisplayResponse>

    @GET("allpublicsongs")
    suspend fun getBestseller2Songs(): Response<DisplayResponse>
}



