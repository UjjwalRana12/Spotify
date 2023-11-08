package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.Adapter.SongAdapter
import com.android.soundlyspotify.data.Song
import com.android.soundlyspotify.R

class search : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var songAdapter: SongAdapter
    private var songList = listOf<Song>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)


        recyclerView = view.findViewById(R.id.firstrecycler)
        songAdapter = SongAdapter(songList)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = songAdapter

        return view
    }
}
