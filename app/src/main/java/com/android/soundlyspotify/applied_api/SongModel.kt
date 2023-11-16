package com.android.soundlyspotify.applied_api

import com.android.soundlyspotify.Song

data class SongModel(
    val name: String,
    val imageMV: Int,
    val id: Int,
    val uploader: String,
    val song_duration: String,
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

data class SongDetails(
    val id: Int,
    val name: String,
    val song_url: String,
    val thumbnail_url: String,
    val is_liked: Boolean,
    val lyrics_url: String,
    val song_duration: String
)
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?
)



