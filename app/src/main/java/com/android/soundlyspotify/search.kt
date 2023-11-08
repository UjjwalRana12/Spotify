package com.android.soundlyspotify
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.android.soundlyspotify.data.Song
import com.android.soundlyspotify.R

class search : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val songList = listOf(
            SongModel(R.drawable.arjitsingh, "Song Title 1", "Artist 1", "Album 1"),
            SongModel(R.drawable.nehakakkar, "Song Title 2", "Artist 2", "Album 2"),
            // Add more SongModel instances as needed
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.firstrecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val songAdapter = SongAdapter(songList)
        recyclerView.adapter = songAdapter

        return view
    }
}
