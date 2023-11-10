package com.android.soundlyspotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.Myadapter.BestSellerAdapter
import com.android.soundlyspotify.models.BestSeller
import kotlinx.coroutines.launch

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var bestSellerAdapter: BestSellerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        // Initialize RecyclerView
        val bestsellerRecyclerView: RecyclerView = view.findViewById(R.id.bestSellerRecyclerView)
        bestsellerRecyclerView.setHasFixedSize(true)
        bestsellerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // Set up BestSellerAdapter
        bestSellerAdapter = BestSellerAdapter(emptyList())
        bestsellerRecyclerView.adapter = bestSellerAdapter

        // Fetch data from API using Retrofit or populate it with some default data
        val apiService: UserAPI.ApiService = RetrofitClient.retrofit.create(UserAPI.ApiService::class.java)

        lifecycleScope.launch {
            try {
                // API request to get songs
                val response = apiService.getSongs()
                if (response.success) {
                    // Extract the list of songs from ResponseData
                    val songs = response.data ?: emptyList()


                    // Update the adapter with the received data
                    bestSellerAdapter.setSongs(songs)
                } else {
                    // Handle API error
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle network or other errors
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }
}


