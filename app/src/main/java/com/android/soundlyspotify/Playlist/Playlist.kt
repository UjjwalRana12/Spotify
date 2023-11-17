package com.android.soundlyspotify.Playlist

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String? = null,
    val artist: String
)

data class UpdatedPlaylist(
    val name: String,
    val description: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String? = null
)

data class DeleteResponse(
    val success: Boolean,
    val message: String
)

data class CreatePlaylistResponse(
    val success: Boolean,
    val message: String,
    val data: Playlist? = null //  Playlist is the data class for a playlist item
)

