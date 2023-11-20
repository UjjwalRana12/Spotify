package com.android.soundlyspotify

data class GameData(
    val success: Boolean,
    val message: String,
    val data: GameDetails
)

data class GameDetails(
    val name: String,
    val id: Int,
    val option1: String,
    val option2: String,
    val audio_1: String
)
data class GameResponse(
    val success: Boolean,
    val message: String
)
