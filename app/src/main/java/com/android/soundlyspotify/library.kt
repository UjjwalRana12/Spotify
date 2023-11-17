package com.android.soundlyspotify
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.models.GridItemData
import com.android.soundlyspotify.Playlist.CreatePlaylist
import com.android.soundlyspotify.Playlist.CreatePlaylistResponse
import com.android.soundlyspotify.Playlist.RetrofitPlaylist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            GridItemData("Item 5", R.drawable.addyoplay),
            // Add more items as needed
        )

        val adapter = GridAdapter(data) { clickedItem ->
            if (clickedItem.text == "Item 5") {
                navigateToAnotherFragment()
            } else {
                addPlaylist(clickedItem)
            }
        }


        recyclerView.adapter = adapter

        return view
    }

    private fun addPlaylist(gridItemData: GridItemData) {
        val playlistName = "Playlist for ${gridItemData.text}"
        val imageUrl = gridItemData.imageUrl

        val newPlaylist = CreatePlaylist(
            date_created = "2023-11-17T12:00:00",  // replace with the actual date
            date_updated = "2023-11-17T12:00:00",
            description = playlistName,
            id = null,  // It's null initially since the server assigns the ID
            is_private = false,
            name = playlistName,
            songs = emptyList(),  // Initially, the playlist might not have any songs
            thumbnail_url = imageUrl,
            uploader = "your_uploader"  // replace with the actual uploader information
        )

        // Show a loading indicator if needed
        // You can implement a loading indicator in your UI here

        // Use Retrofit to make the API call
        val playlistService = RetrofitPlaylist.playlistService
        playlistService.createPlaylist(newPlaylist)
            .enqueue(object : Callback<CreatePlaylistResponse> {
                override fun onResponse(
                    call: Call<CreatePlaylistResponse>,
                    response: Response<CreatePlaylistResponse>
                ) {
                    // Hide the loading indicator if needed
                    // You can implement hiding the loading indicator in your UI here

                    if (response.isSuccessful) {
                        val createdPlaylist = response.body()?.data

                        println("Added Playlist: Name - ${createdPlaylist?.name}, Image URL - ${createdPlaylist?.thumbnail_url}")

                        showToast("Playlist created successfully")
                    } else {
                        // Handle the error case
                        println("Failed to create playlist. Error code: ${response.code()}")

                        showToast("Failed to create playlist. Please try again.")
                    }
                }

                override fun onFailure(call: Call<CreatePlaylistResponse>, t: Throwable) {
                    // Hide the loading indicator if needed
                    // You can implement hiding the loading indicator in your UI here

                    // Handle the network failure
                    println("Network failure: ${t.message}")
                    // Show a generic error message to the user
                    showToast("Network failure. Please check your internet connection.")
                }
            })

        // Show a loading indicator if needed
        // You can implement a loading indicator in your UI here
    }
    private fun navigateToAnotherFragment() {
        val anotherFragment = Add_Songs()
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

        // Replace the current fragment with AnotherFragment
        transaction.replace(R.id.librarykaframe, anotherFragment)

        // Add the transaction to the back stack so the user can navigate back
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }
    private fun showToast(message: String) {


        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}



