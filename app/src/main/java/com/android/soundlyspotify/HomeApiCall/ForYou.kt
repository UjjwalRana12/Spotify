package com.android.soundlyspotify.HomeApiCall

data class Apiforyou(
    val success: Boolean,
    val message: String,
    val data: List<ForYou>
)

data class ForYou(
    val name: String,
    val id: Int,
    val uploader: String,
    val language: String,
    val song_duration: String,
    val mood: String,
    val genre: String,
    val thumbnail_url: String,
    val artist: String,
    val is_private: Boolean,
    val is_liked: Boolean
)
