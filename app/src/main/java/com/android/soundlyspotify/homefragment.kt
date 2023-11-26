package com.android.soundlyspotify

import CarouselAdapter
import android.content.Context
import android.os.Bundle
import com.android.soundlyspotify.applied_api.SongModel
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
import androidx.viewpager2.widget.ViewPager2
//import com.android.soundlyspotify.Myadapter.BestSeller2Adapter

import com.android.soundlyspotify.Myadapter.BestSellerAdapter
import com.android.soundlyspotify.Myadapter.ClothingAdapter
import com.android.soundlyspotify.Myadapter.ImagePagerAdapter
import com.android.soundlyspotify.Myadapter.MusicAdapter
import com.android.soundlyspotify.Myadapter.OfferAdapter
import com.android.soundlyspotify.applied_api.ApiSongResponse
import com.android.soundlyspotify.applied_api.RetroClient
import com.android.soundlyspotify.applied_api.SongDetails


import com.android.soundlyspotify.data.RetrofitDisplay
import com.android.soundlyspotify.data.RetrofitDisplay.songApiService
import com.android.soundlyspotify.data.SongApiService
import com.android.soundlyspotify.models.BestSeller
import com.android.soundlyspotify.models.BestSeller2
import com.android.soundlyspotify.models.Clothing
import com.android.soundlyspotify.models.MyItem
import com.android.soundlyspotify.models.Offer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

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
        }, 1000, 2000)

        // closuer apply
        val viewPagerCarousel = view.findViewById<ViewPager2>(R.id.viewPager2)
        val carouselAdapter = CarouselAdapter(requireContext())
        viewPagerCarousel.adapter = carouselAdapter







        //OFFER RECYCLERVIEW AND ALSO HAS AN API

        // Initialize RecyclerViews and set their properties
        offerRecyclerView = view.findViewById(R.id.offerRecyclerView)
        offerRecyclerView.setHasFixedSize(true)
        offerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val fixedQueriesOffer = listOf("abcd", "aashke", "angreji beat", "brown rang", "2phone")
        val fixedQueriesbestseller = listOf("nasha", "abcd", "aashke", "abcd", "fairplay")
        val fixedQueriesmusic = listOf("abcd", "nasha", "abcd", "abcd", "angreji beat")
        val fixedQueriesclothing = listOf("abcd", "nasha", "abcd", "abcd", "angreji beat")


        val offers = listOf(
            Offer(R.drawable.phototeen, "Offer Title 1", "abcd"),
            Offer(R.drawable.photoek, "Offer Title 2", "ashke"),
            Offer(R.drawable.photocheh, "Offer Title 3", "angreji beat"),
            Offer(R.drawable.photodo, "Offer Title 4", "brown rang"),
            Offer(R.drawable.photopaanch, "Offer Title 5", "2phone"),
            // Add more items as needed
        )

        val offerAdapter = OfferAdapter { clickedOffer ->

            // Example: Logging the query for demonstration purposes
            println("Item clicked 1")

        }


        offerAdapter.setOnItemClickListener(object : OfferAdapter.OnItemClickListener {
            override fun onItemClick(clickedOffer: Offer) {
                // Handle item click
                // You can use the properties of clickedOffer or perform other actions
                Log.d("OfferAdapter", "Item clicked:  Title: ${clickedOffer.title}, Query: ${clickedOffer.query}")
            }
        })
        println("the offer api start calling")
        // Inside your CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetroClient.instance
                val token = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE).getString("access_token", null)

                if (token == null) {
                    Log.e("homefragment", "Access token is null")
                } else {
                    Log.d("homefragment", "Access token: $token")
                    RetroClient.updateAccessToken(token)
                }

                val apiResponses = fixedQueriesOffer.map { query ->
                    apiService.searchSongs(query)
                }

                apiResponses.forEachIndexed { index, response ->
                    Log.d("homefragment", "API Response for query '${fixedQueriesOffer[index]}': $response")
                }

                // Convert apiResponses to a List<Offer> or extract the necessary data
                val updatedOffers = updateItemsWithApiData(offers, apiResponses)

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    offerAdapter.updateOffers(updatedOffers)
                    Log.d("RecyclerView", "Data size: ${updatedOffers.size}")
                    offerAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("homefragment", "Error fetching offer data: ${e.message}")
            }
        }
        offerRecyclerView.adapter = offerAdapter
















        // BEST SELLER RECYCLER VIEW AND IT ALSO HAS AN API
        // BESTSELLER IS HERE
        bestsellerRecyclerView = view.findViewById(R.id.bestSellerRecyclerView)
        bestsellerRecyclerView.setHasFixedSize(true)
        bestsellerRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // Sample list of BestSeller items
        val bestSellers = listOf(
            BestSeller(R.drawable.photoek, "Title 1", "Query 1"),
            BestSeller(R.drawable.photodo, "Title 2", "Query 2"),
            BestSeller(R.drawable.phototeen, "Title 3", "Query 3"),
            BestSeller(R.drawable.photochaar, "Title 4", "Query 4"),
            BestSeller(R.drawable.photopaanch, "Title 5", "Query 5")
            // Add more items as needed
        )

        // setting up bestseller adapter
        val bestSellerAdapter = BestSellerAdapter(bestSellers)


        bestSellerAdapter.setOnItemClickListener(object : BestSellerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, query: String) {

                // Handle item click here
                val clickedBestSeller = bestSellers[position]

                // Log the query to Logcat
                Log.d("ItemClicked", "Query: $query")

                // Show a Toast message with the query
                Toast.makeText(
                    requireContext(),
                    "Clicked on ${clickedBestSeller.title}, Query: $query",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
        println("best seller api initiated")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetroClient.instance
                val token = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE).getString("access_token", null)

                if (token == null) {
                    Log.e("homefragment", "Access token is null")
                } else {
                    Log.d("homefragment", "Access token: $token")
                    RetroClient.updateAccessToken(token)
                }

                val apiResponses = fixedQueriesbestseller.map { query ->
                    apiService.searchSongs(query)
                }

                apiResponses.forEachIndexed { index, response ->
                    Log.d("homefragment", "API Response for query '${fixedQueriesbestseller[index]}': $response")
                }

                val newBestSellers = apiResponses.mapIndexed { index, response ->
                    // Extract data from each response and create BestSeller objects
                    BestSeller(R.drawable.defaultimage, "Title $index", "Query $index")
                    // Replace the placeholders with the actual data from your API response
                }
                withContext(Dispatchers.Main) {
                    bestSellerAdapter.updateData(newBestSellers)
                    bestSellerAdapter.notifyDataSetChanged()
                }



            } catch (e: Exception) {
                // Handle error
                Log.e("homefragment", "Error fetching offer data: ${e.message}")
            }
        }

        bestsellerRecyclerView.adapter = bestSellerAdapter






















        // CLOTHING RECYCLER VIEW AND API IS ALSO DONE
        // clothing is here


        val clothingRecyclerView = view.findViewById<RecyclerView>(R.id.clothingRecyclerView)
        clothingRecyclerView.setHasFixedSize(true)
        clothingRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val clothingList = listOf(
            Clothing(R.drawable.photoek, "listen this", "query1"),
            Clothing(R.drawable.phototeen, "Casual song", "query2"),
            Clothing(R.drawable.photopaanch, "Formal", "query3"),
            Clothing(R.drawable.photochaar, "Formal", "query4"),
            Clothing(R.drawable.photodo, "Formal", "query5")
            // Add more items as needed
        )

        val clothingAdapter = ClothingAdapter(requireContext())
        clothingRecyclerView.adapter = clothingAdapter

// Set item click listener
        clothingAdapter.setOnItemClickListener(object : ClothingAdapter.OnItemClickListener {
            override fun onItemClick(query: String) {
                // Handle item click here
                // For now, let's just print the query
                Log.d("ItemClicked", "Query: $query")
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetroClient.instance
                val token = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE).getString("access_token", null)

                if (token == null) {
                    Log.e("homefragment", "Access token is null")
                } else {
                    Log.d("homefragment", "Access token: $token")
                    RetroClient.updateAccessToken(token)
                }

                val apiResponses = fixedQueriesclothing.map { query ->
                    apiService.searchSongs(query)
                }

                apiResponses.forEachIndexed { index, response ->
                    Log.d("homefragment", "API Response for query '${fixedQueriesclothing[index]}': $response")
                }

                // Convert apiResponses to a List<Offer> or extract the necessary data
                val newClothingList = apiResponses.mapIndexed { index, response ->
                    // Replace the placeholders with the actual data from your API response
                    Clothing(R.drawable.defaultimage, "Title $index", "Query $index")
                }

// Switch to the main thread to update the UI
                withContext(Dispatchers.Main) {
                    // Update the UI with the transformed data
                    clothingAdapter.updateList(newClothingList)
                }

            } catch (e: Exception) {
                // Handle error
                Log.e("homefragment", "Error fetching offer data: ${e.message}")
            }
        }

        clothingAdapter.submitList(clothingList)






















        // BEST SELLER 2
        bestseller2RecyclerView = view.findViewById(R.id.bestSeller2RecyclerView)
        bestseller2RecyclerView.setHasFixedSize(true)
        bestseller2RecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val bestSellersList = listOf(
            BestSeller2(R.drawable.arjit_imgg, "Song 1", "Query 1"),
            BestSeller2(R.drawable.badshah_img, "Song 2", "Query 2"),
            BestSeller2(R.drawable.honey_img, "Song 3", "Query 3"),
            BestSeller2(R.drawable.darshan_img, "Song 3", "Query 3"),
            BestSeller2(R.drawable.neha_img, "Song 3", "Query 3"),
            // Add more items as needed
        )


        val adapter = BestSeller2Adapter(requireContext(), bestSellersList)


// Set the item click listener
        adapter.setOnItemClickListener(object : BestSeller2Adapter.OnItemClickListener {
            override fun onItemClick(item: BestSeller2) {
                // Handle item click here
                Log.d("ItemClicked", "Query: ${item.query}")
                Toast.makeText(
                    requireContext(),
                    "Item clicked: ${item.title}, Query: ${item.query}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        bestseller2RecyclerView.adapter = adapter























        //MUSIC ADAPTER
        // music adapter is here

        val recyclerView: RecyclerView = view.findViewById(R.id.musicoRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val itemList = listOf(
            MyItem("musico 1", R.drawable.photoek, "query1"),
            MyItem("musico 2", R.drawable.photoek, "query2"),
            MyItem("musico 3", R.drawable.photoek, "query3"),
            // Add more items as needed
        )

        val adapterss = MusicAdapter(itemList.toMutableList()) { clickedQuery ->
            onSongClicked(clickedQuery)
        }



        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetroClient.instance
                val token = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE).getString("access_token", null)

                if (token == null) {
                    Log.e("homefragment", "Access token is null")
                } else {
                    Log.d("homefragment", "Access token: $token")
                    RetroClient.updateAccessToken(token)
                }

                val apiResponses = fixedQueriesmusic.map { query ->
                    apiService.searchSongs(query)
                }

                apiResponses.forEachIndexed { index, response ->
                    Log.d("homefragment", "API Response for query '${fixedQueriesmusic[index]}': $response")
                }

                // Convert apiResponses to a List<Offer> or extract the necessary data
                val newItems = apiResponses.mapIndexed { index, response ->
                    // Replace the placeholders with the actual data from your API response
                    MyItem("Title $index", R.drawable.defaultimage, "Query $index")
                }

                // Switch to the main thread to update the UI
                withContext(Dispatchers.Main) {
                    // Update the adapter with the transformed data
                    adapterss.updateData(newItems)
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("homefragment", "Error fetching offer data: ${e.message}")
            }
        }

        recyclerView.adapter = adapterss


        return view
    }

    private fun updateItemsWithApiData(offers: List<Offer>, apiResponses: List<ApiSongResponse>): List<Offer> {
        return offers.mapIndexed { index, offer ->
            if (index < apiResponses.size && index < apiResponses[index].data.size) {
                // Update the corresponding Offer item with API response data
                val songModel = apiResponses[index].data[index]
                offer.copy(
                    title = songModel.name,
                    image = songModel.imageMV
                    // Add more properties as needed
                )
            } else {
                // If there's no corresponding API response, return the original Offer
                offer
            }
        }
    }

    private fun onSongClicked(song: SongModel) {
        // Perform actions when a song is clicked
        playSong(song)
    }


    private fun playSong(song: SongModel) {

        val newToken = "new_access_token"
        println("Play song pe clicked")

        // Update the access token in RetrofitClient
        RetrofitClient.updateAccessToken(newToken)

        lifecycleScope.launch {
            try {
                // Use the details from the clicked song to make an API call
                println("song clicked working 1")
               // val apiResponse = SongApiService.getSongDetails(song.id)
                val apiResponse: Response<ApikaResponse<SongDetails>> = songApiService.getSongDetails(song.id)
                println("song clicked working 2")
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
                               println("No song")
                            }
                        } else {
                            // Handle the case where the API call was not successful
                            println("No song")
                        }
                    } else {
                        // Handle the case where the HTTP request itself failed
                        println("No song")
                    }
                }
            } catch (e: HttpException) {
                println("No song")
            } catch (e: Exception) {
                // Handle other exceptions
                println("No song")
            }
        }
    }



}