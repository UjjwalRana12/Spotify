package com.android.soundlyspotify.data

data class Song(
    val id: Int,
    val name: String,
    val artist: String,
    val language: String,
    val mood: String,
    val genre: String,
    val thumbnailUrl: String?
)
