package com.android.soundlyspotify.data

data class SongDisplayed(
    val name: String,
    val id: Int,
    val uploader: String,
    val language: String,
    val mood: String,
    val genre: String,
    val thumbnailUrl: String,
    val artist: String,
    val isPrivate: Boolean,
    val isLiked: Boolean
)


data class DisplayResponse(
    val success: Boolean,
    val message: String,
    val data: List<SongDisplayed>
)

