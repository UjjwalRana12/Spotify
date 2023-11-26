package com.android.soundlyspotify.data

import com.android.soundlyspotify.ApikaResponse
import com.android.soundlyspotify.applied_api.ApiSongResponse
import com.android.soundlyspotify.applied_api.SongDetails

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface SongApi {
    @GET("songsearch")
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
interface SongApiService {

    @GET("getsong/{id}")
    suspend fun getSongDetails(@Path("id") songId: String): Response<ApikaResponse<SongDetails>>

}





