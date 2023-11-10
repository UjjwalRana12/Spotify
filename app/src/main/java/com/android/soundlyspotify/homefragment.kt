package com.android.soundlyspotify

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.soundlyspotify.Myadapter.BestSeller2Adapter
import com.android.soundlyspotify.Myadapter.BestSellerAdapter
import com.android.soundlyspotify.Myadapter.CarouselAdapter
import com.android.soundlyspotify.Myadapter.ClothingAdapter
import com.android.soundlyspotify.Myadapter.ImagePagerAdapter
import com.android.soundlyspotify.Myadapter.OfferAdapter
import com.android.soundlyspotify.models.BestSeller
import com.android.soundlyspotify.models.BestSeller2
import com.android.soundlyspotify.models.Clothing
import com.android.soundlyspotify.models.Offer
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import com.android.soundlyspotify.RetrofitClient.userAPI

import com.android.soundlyspotify.RetrofitClient

class homefragment : Fragment() {

    private lateinit var offerRecyclerView: RecyclerView
    private lateinit var bestsellerRecyclerView: RecyclerView
    private lateinit var clothingRecyclerView: RecyclerView
    private lateinit var bestseller2RecyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var imageAdapter: ImagePagerAdapter
    private val imageList = listOf(
        R.drawable.pager1,
        R.drawable.pager2,
        R.drawable.pager3
    )
    private var currentPage = 0
    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

        //View pager 1st
        viewPager = view.findViewById(R.id.viewPager)
        imageAdapter = ImagePagerAdapter(requireContext(), imageList)
        viewPager.adapter = imageAdapter

        val handler = Handler()
        val update = Runnable {
            if (currentPage == imageList.size) {
                currentPage = 0
            }
            viewPager.currentItem = currentPage++
        }

        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 1000, 3000)

            // closuer apply
        val viewPagerCarousel = view.findViewById<ViewPager>(R.id.viewPager2)
        val carouselAdapter = CarouselAdapter(requireContext())
        viewPagerCarousel.adapter = carouselAdapter


        // Initialize RecyclerViews and set their properties
        offerRecyclerView = view.findViewById(R.id.offerRecyclerView)
        offerRecyclerView.setHasFixedSize(true)
        offerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val offers = listOf(
            Offer(R.drawable.phototeen, "Offer Title 1"),
            Offer(R.drawable.photoek, "Offer Title 2"),
            Offer(R.drawable.photocheh, "Offer Title 3"),
            Offer(R.drawable.photodo, "Offer Title 3"),
            Offer(R.drawable.photopaanch, "Offer Title 3"),
            // Add more items as needed
        )
        val offerAdapter = OfferAdapter(offers)
        offerRecyclerView.adapter = offerAdapter




        // BESTSELLER IS HERE

        lateinit var bestSellerAdapter: BestSellerAdapter
        bestsellerRecyclerView = view.findViewById(R.id.bestSellerRecyclerView)
        bestsellerRecyclerView.setHasFixedSize(true)
        bestsellerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

// Fetch data from API using Retrofit or populate it with some default data
        val apiService: UserAPI.ApiService = RetrofitClient.retrofit.create(UserAPI.ApiService::class.java)

        lifecycleScope.launch {
            try {
                // API request to get songs
                val response = apiService.getSongs()
                if (response.success) {
                    // Update the adapter with the received data
                    bestSellerAdapter.setSongs(response.data ?: emptyList())
                } else {
                    // Handle API error
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle network or other errors
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        }


        // clothing is here



        clothingRecyclerView = view.findViewById(R.id.clothingRecyclerView)
        clothingRecyclerView.setHasFixedSize(true)
        clothingRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val context: Context = requireContext()// suggested by chatgpt to encounter errors
        val clothingList = listOf(
            Clothing(R.drawable.photoek, "listen this "),
            Clothing(R.drawable.phototeen, "Casual song"),
            Clothing(R.drawable.photopaanch, "Formal "),
            Clothing(R.drawable.photochaar, "Formal "),
            Clothing(R.drawable.photodo, "Formal "),
            // Add more  items as needed
        )

        val clothingAdapter = ClothingAdapter(context,clothingList)
        clothingRecyclerView.adapter = clothingAdapter







        bestseller2RecyclerView = view.findViewById(R.id.bestSeller2RecyclerView)
        bestseller2RecyclerView.setHasFixedSize(true)
        bestseller2RecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val bestSellersList = listOf(
            BestSeller2(R.drawable.photosaath, "arjit singh"),
            BestSeller2(R.drawable.photocheh, "arjit Awaits"),
            BestSeller2(R.drawable.photoek, "arjit"),
            BestSeller2(R.drawable.phototeen, "arjit"),
            BestSeller2(R.drawable.photochaar, "arjit"),
            // Add item
        )
        val adapter = BestSeller2Adapter(context, bestSellersList)
        bestseller2RecyclerView.adapter = adapter






        return view
    }
}
