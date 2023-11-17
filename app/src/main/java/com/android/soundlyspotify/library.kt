package com.android.soundlyspotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.models.GridItemData

class library : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewgridlibrary)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        val data = listOf(
            GridItemData("Item 1", R.drawable.photochaar),
            GridItemData("Item 2", R.drawable.photoek),
            GridItemData("Item 3", R.drawable.photoek),
            GridItemData("Item 4", R.drawable.photoek),
            GridItemData("Item 5", R.drawable.photoek),
            // Add more items as needed
        )

        val adapter = GridAdapter(data) { clickedItem ->
            addPlaylist(clickedItem)
        }

        recyclerView.adapter = adapter

        return view
    }

    private fun addPlaylist(gridItemData: GridItemData) {

        // Example logic:
        val playlistName = "Playlist for ${gridItemData.text}"
        val imageUrl = gridItemData.imageUrl

        // Now, you can use the playlist information to perform the desired action,
        // such as adding the playlist to your application's playlist collection or
        // navigating to a new screen to display the playlist details.

        // Example: Print the playlist information
        println("Added Playlist: Name - $playlistName, Image URL - $imageUrl")

        // You can customize this logic based on your application's requirements.
        // For instance, you might want to use a database to store playlists or
        // trigger a network request to add the playlisist to add a server



        // Implement the logic to add a playlist based on the clicked item
        // For example, you can use gridItemData.text and gridItemData.imageUrl
        // to get information about the clicked item and add it to the playlist.
        // Add your specific logic here.
    }
}