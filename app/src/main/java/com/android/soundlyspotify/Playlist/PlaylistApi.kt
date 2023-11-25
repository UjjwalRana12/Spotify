package com.android.soundlyspotify.Playlist

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaylistService {

    @POST("createPlaylist")
    fun createPlaylist(@Body playlist: CreatePlaylist): Call<CreatePlaylistResponse>


    // @GET("getPlaylist/{playlistId}")
    // fun getPlaylist(@Path("playlistId") playlistId: Int): Call<PlaylistResponse>


}
