package com.android.soundlyspotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.Myadapter.AddMySongAdapter
import com.android.soundlyspotify.models.AddSong


class Add_Songs : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming you have a list of AddSong objects
        val dataList = generateDataList()

        val recyclerView: RecyclerView = view.findViewById(R.id.addSongRecycler)
        val adapter = AddMySongAdapter(dataList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun generateDataList(): List<AddSong> {

        return listOf(
            AddSong(R.drawable.defaultimage, "Song 1", "Artist 1", "3:45"),
            AddSong(R.drawable.photosaath, "Song 2", "Artist 2", "4:20"),
            AddSong(R.drawable.photoek, "Song 2", "Artist 2", "4:20"),
            AddSong(R.drawable.photodo, "Song 2", "Artist 2", "4:20"),
            AddSong(R.drawable.photochaar, "Song 3", "Artist 3", "5:10")
        )
    }
}