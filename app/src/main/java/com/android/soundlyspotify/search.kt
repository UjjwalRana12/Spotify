package com.android.soundlyspotify

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        songAdapter = SongAdapter(mutableListOf()) // Initialize with a MutableList
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
                    delay(1000) // Increase the delay to 1000 milliseconds (1 second)
                    Log.d(TAG, "Delay completed")
                    newText?.let {
                        Log.d(TAG, "Calling searchSongs with query: $it")
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
                    songAdapter.setData(apiResults.toMutableList()) // Convert to MutableList
                    Log.d(TAG, "Search successful. Number of results: ${apiResults.size}")
                } else {
                    // Log the failure message from the API response
                    Log.e(TAG, "Search failed. API message: ${apiResponse.message}")
                }
            } catch (e: HttpException) {
                // Handle HTTP errors
                when (e.code()) {
                    404 -> Log.e(TAG, "Resource not found: ${e.message()}")
                    else -> Log.e(TAG, "HTTP error: ${e.code()} ${e.message()}")
                }
            } catch (e: Exception) {
                // Handle general exceptions
                Log.e(TAG, "Error fetching data: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "search"
    }
}