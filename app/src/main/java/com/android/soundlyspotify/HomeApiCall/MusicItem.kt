package com.android.soundlyspotify.HomeApiCall

data class MusicItem(
    val name: String,
    val id: Int,
    val uploader: String,
    val language: String,
    val songDuration: String,
    val mood: String,
    val genre: String,
    val thumbnailUrl: String,
    val artist: String,
    val isPrivate: Boolean,
    val isLiked: Boolean,
    val query: String // Add any additional properties as needed
)
