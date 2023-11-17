package com.android.soundlyspotify.Playlist

import com.android.soundlyspotify.Song


data class CreatePlaylist(
    val date_created: String,
    val date_updated: String,
    val description: String,
    val id: String?,
    val is_private: Boolean,
    val name: String,
    val songs: List<Song>,
    val thumbnail_url: Int,
    val uploader: String
)

data class CreatePlaylistResponse(
    val data: CreatePlaylist,
    val message: String,
    val success: Boolean
)
