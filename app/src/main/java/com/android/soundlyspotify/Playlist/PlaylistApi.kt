package com.android.soundlyspotify.Playlist

import retrofit2.Call
import retrofit2.http.*

interface PlaylistApi {

    @GET("playlists/{id}")
    fun getPlaylist(@Path("id") playlistId: Int): Call<Playlist>


    @POST("playlists")
    fun createPlaylist(@Body playlist: Playlist): Call<CreatePlaylistResponse>

    @PATCH("playlists/{id}")
    fun updatePlaylist(@Path("id") playlistId: Int, @Body updatedPlaylist: Playlist): Call<Playlist>

    @DELETE("playlists/{id}")
    fun deletePlaylist(@Path("id") playlistId: Int): Call<Void>
}
