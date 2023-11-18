package com.android.soundlyspotify.models

// these are the data classes for my recyclerView
data class BestSeller(val image: Int, val title: String,val query: String)
data class Offer(val image: Int, val title: String, val query: String)


data class Clothing(val image: Int, val title: String,val query: String)

data class BestSeller2(val image: Int, val title: String,val query: String)

data class GridItemData(val text: String, val imageUrl: Int)

data class MyItem(val text: String, val imageUrl: Int,val query: String)

data class AddSong(
    val imageResource: Int,  // Resource ID for the song image
    val songName: String,
    val artist: String,
    val timing: String
)
