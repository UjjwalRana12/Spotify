package com.android.soundlyspotify

import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.android.soundlyspotify.applied_api.RetroClient
import com.android.soundlyspotify.applied_api.SongModel

class search : Fragment() {

    private lateinit var songAdapter: SongAdapter
    private val songApi = RetroClient.instance
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.firstrecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the SongAdapter with an empty list and an item click listener
        songAdapter = SongAdapter(mutableListOf(), object : SongAdapter.OnItemClickListener {
            override fun onItemClick(song: SongModel) {
                // Handle item click here, e.g., make another API call
                onSongClicked(song)
            }
        })

        recyclerView.adapter = songAdapter

        val searchView: SearchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission (if needed)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query changes
                searchJob?.cancel()

                searchJob = lifecycleScope.launch {
                    Log.d(TAG, "Delay started")
                    delay(2000)
                    Log.d(TAG, "Delay completed")
                    newText?.let {
                        Log.d(TAG, "Calling searchSongs with query: $it")
                        hideErrorFragment()
                        searchSongs(it)
                    }
                }

                return true
            }
        })

        Log.d(TAG, "API called with search")

        return view
    }

    private fun searchSongs(query: String) {
        lifecycleScope.launch {
            try {
                val apiResponse = songApi.searchSongs(query)

                if (apiResponse.success) {
                    Log.d(TAG, "API call successfully")
                    val apiResults = apiResponse.data
                    if (apiResults.isNotEmpty()) {
                        songAdapter.setData(apiResults.toMutableList())
                        Log.d(TAG, "Search successful. Number of results: ${apiResults.size}")
                    } else {
                        showEmptyResultFragment()
                    }
                } else {
                    showErrorFragment()
                    Log.e(TAG, "Search failed. API message: ${apiResponse.message}")
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    404 -> {
                        showNotFoundErrorFragment()
                        Log.e(TAG, "Resource not found: ${e.message()}")
                    }
                    else -> {
                        showErrorFragment()
                        Log.e(TAG, "HTTP error: ${e.code()} ${e.message()}")
                    }
                }
            } catch (e: Exception) {
                showErrorFragment()
                Log.e(TAG, "Error fetching data: ${e.message}")
            }
        }
    }

    private fun onSongClicked(song: SongModel) {
        // Perform actions when a song is clicked
        // For example, make another API call using the song details
        makeAnotherApiCall(song)
    }

    private fun makeAnotherApiCall(song: SongModel) {
        // Implement the logic to make another API call based on the selected song
        // You can use the song details (e.g., song.id) to customize the request
        // Example: songApi.getSongDetails(song.id)
        // Handle the response as needed
    }

    private fun showEmptyResultFragment() {
        // Implementation for showing an empty result fragment
    }

    private fun showNotFoundErrorFragment() {
        // Implementation for showing a not found error fragment
    }

    private fun showErrorFragment() {
        // Implementation for showing a general error fragment
    }

    private fun hideErrorFragment() {
        // Implementation for hiding the error fragment
    }

    companion object {
        private const val TAG = "search"
    }
}
