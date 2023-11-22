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
import com.android.soundlyspotify.data.RetrofitDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class search : Fragment() {

    private lateinit var songAdapter: SongAdapter
    private val songApi = RetroClient.instance
    private var searchJob: Job? = null
    private var SongApiService =RetrofitDisplay.songApiService


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
        playSong(song)
    }

    private fun playSong(song: SongModel) {
        lifecycleScope.launch {
            try {
                // Use the details from the clicked song to make an API call
                val apiResponse = SongApiService.getSongDetails(song.id)

                withContext(Dispatchers.Main) {
                    // Handle the response as needed
                    if (apiResponse.isSuccessful) {
                        val songApiResponse = apiResponse.body()
                        if (songApiResponse != null && songApiResponse.success) {
                            val songDetails = songApiResponse.data
                            if (songDetails != null) {
                                val songUrl = songDetails.song_url

                                // Create an instance of your new fragment and pass the song URL as an argument
                                val newFragment = playerfragment.createInstance(songUrl)

                                // Replace the current fragment with the new fragment
                                val fragmentManager = requireActivity().supportFragmentManager
                                fragmentManager.beginTransaction()
                                    .replace(R.id.search_frag, newFragment)
                                    .addToBackStack(null)  // Add to the back stack for back navigation if needed
                                    .commit()
                            } else {
                                Log.e(TAG, "Response data is null")
                            }
                        } else {
                            // Handle the case where the API call was not successful
                            Log.e(TAG, "Failed to fetch additional details. API message: ${songApiResponse?.message}")
                        }
                    } else {
                        // Handle the case where the HTTP request itself failed
                        Log.e(TAG, "HTTP error: ${apiResponse.code()} ${apiResponse.message()}")
                    }
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error: ${e.code()} ${e.message}", e)
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e(TAG, "There is Error fetching additional details", e)
            }
        }
    }



    private fun showEmptyResultFragment() {
        val transaction = parentFragmentManager.beginTransaction()

        // Replace the current fragment with the not found fragment and add a tag
        val notFoundFragment = aayein()
        transaction.addToBackStack("not_found")
        transaction.replace(R.id.aayein_frame, notFoundFragment, "not_found")
        transaction.commit()
    }

    private fun showNotFoundErrorFragment() {
        val transaction = parentFragmentManager.beginTransaction()

        // Replace the current fragment with the not found fragment and add a tag
        val notFoundFragment = aayein()
        transaction.addToBackStack("not_found")
        transaction.replace(R.id.aayein_frame, notFoundFragment, "not_found")
        transaction.commit()
    }

    private fun showErrorFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.addToBackStack("error_fragment")
        // Replace the current fragment with the error fragment and add a tag
        val errorFragment = aayein()

        transaction.replace(R.id.aayein_frame, errorFragment, "error_fragment")
        transaction.commit()
    }

    private fun hideErrorFragment() {
        val fragmentManager = parentFragmentManager
        val errorFragment = fragmentManager.findFragmentByTag("not_found")

        errorFragment?.let {
            val transaction = fragmentManager.beginTransaction()

            transaction.remove(it)
            transaction.commit()
        }
    }

    companion object {
        private const val TAG = "search"
    }
}