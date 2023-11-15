package com.android.soundlyspotify.applied_api

import com.android.soundlyspotify.Song

data class SongModel(
    val name: String,
    val imageMV: Int,
    val uploader: String,
    val language: String,
    val lyricsUrl: String?,
    val mood: String,
    val genre: String,
    val thumbnailUrl: String,
    val artist: String,
    val isPrivate: Boolean,
    val isLiked: Boolean
)

data class ApiSongResponse(
    val success: Boolean,
    val message: String,
    val data: List<SongModel>
)



