package com.android.soundlyspotify

import CarouselAdapter
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
import androidx.viewpager2.widget.ViewPager2
//import com.android.soundlyspotify.Myadapter.BestSeller2Adapter

import com.android.soundlyspotify.Myadapter.BestSellerAdapter
import com.android.soundlyspotify.Myadapter.ClothingAdapter
import com.android.soundlyspotify.Myadapter.ImagePagerAdapter
import com.android.soundlyspotify.Myadapter.MusicAdapter
import com.android.soundlyspotify.Myadapter.OfferAdapter
import com.android.soundlyspotify.data.DisplayInterface
import com.android.soundlyspotify.data.RetrofitDisplay
import com.android.soundlyspotify.data.SongDisplayed
import com.android.soundlyspotify.models.BestSeller
import com.android.soundlyspotify.models.Clothing
import com.android.soundlyspotify.models.MyItem
import com.android.soundlyspotify.models.Offer
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class homefragment : Fragment() {

    private lateinit var offerRecyclerView: RecyclerView
    private lateinit var bestsellerRecyclerView: RecyclerView
    private lateinit var clothingRecyclerView: RecyclerView
    private lateinit var bestseller2RecyclerView: RecyclerView

    private lateinit var displayInterface: DisplayInterface
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
        val viewPagerCarousel = view.findViewById<ViewPager2>(R.id.viewPager2)
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
            Offer(R.drawable.photodo, "Offer Title 4"),
            Offer(R.drawable.photopaanch, "Offer Title 5"),
            // Add more items as needed
        )

        val offerAdapter = OfferAdapter(offers) { clickedOffer ->
            // Handle item click here

            Toast.makeText(requireContext(), "Item clicked: ${clickedOffer.title}", Toast.LENGTH_SHORT).show()
        }

        offerRecyclerView.adapter = offerAdapter



        // BESTSELLER IS HERE
        bestsellerRecyclerView = view.findViewById(R.id.bestSellerRecyclerView)
        bestsellerRecyclerView.setHasFixedSize(true)
        bestsellerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val bestSellers = listOf(
            BestSeller(R.drawable.photoek, "Title 1"),
            BestSeller(R.drawable.photodo, "Title 2"),
            BestSeller(R.drawable.phototeen, "Title 3"),
            BestSeller(R.drawable.photochaar, "Title 3"),
            BestSeller(R.drawable.photopaanch, "Title 3"),
            // or item add krlo agar krna ho toh


            // Assuming you have a list of BestSeller objects named bestSellers
            // val bestSellers: List<BestSeller> = // Populate this with your data
            //this will help me to put api
        )

        // setting up bestseller adapter
        val bestSellerAdapter = BestSellerAdapter(bestSellers)
        bestsellerRecyclerView.adapter = bestSellerAdapter

        bestSellerAdapter.setOnItemClickListener(object : BestSellerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                val clickedBestSeller = bestSellers[position]
                Toast.makeText(
                    requireContext(),
                    "Clicked on ${clickedBestSeller.title}",
                    Toast.LENGTH_SHORT
                ).show()
                // Navigate to the next fragment
//                val nextFragment = Game // Replace with the actual fragment i want to navigate to
//                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.home_fragment, nextFragment) // Replace fragmentContainer with your actual container ID
//                fragmentTransaction.addToBackStack(null) // This allows the user to navigate back
//                fragmentTransaction.commit()
            }
        })






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

        clothingAdapter.submitList(clothingList)


        // BEST SELLER 2
        displayInterface = RetrofitDisplay.instance
        println("Display interface running")

        // BEST SELLER 2
        bestseller2RecyclerView = view.findViewById(R.id.bestSeller2RecyclerView)
        bestseller2RecyclerView.setHasFixedSize(true)

        // Set the adapter
        val adapter = BestSeller2Adapter(requireContext(), emptyList())
        bestseller2RecyclerView.adapter = adapter

        bestseller2RecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        println("display 2nd step working ")


        lifecycleScope.launch {
            try {
                println("try working")
                val response = displayInterface.getBestseller2Songs()
                if (response.isSuccessful) {
                    println("Bestseller is successful called")
                    val bestSellersList = response.body()?.data ?: emptyList()

                    val adapter = BestSeller2Adapter(requireContext(), bestSellersList)
                    bestseller2RecyclerView.adapter = adapter

                    // Set the item click listener
                    adapter.setOnItemClickListener(object : BestSeller2Adapter.OnItemClickListener {
                        override fun onItemClick(item: SongDisplayed) {
                            // Handle item click here
                            Toast.makeText(
                                requireContext(),
                                "Item clicked: ${item.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                } else {
                    // Handle error
                    Log.e("API Request", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("API Request", "Exception: ${e.message}")
            }
        }





        //MUSIC ADAPTER
        // music adapter is here

        val recyclerView: RecyclerView = view.findViewById(R.id.musicoRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val itemList = listOf(
            MyItem("musico 1", R.drawable.photoek),
            MyItem("musico 2", R.drawable.photoek),
            MyItem("musico 3", R.drawable.photoek),
            // Add more items as needed
        )

        val adapterss = MusicAdapter(itemList) { clickedItem ->
            // Handle item click here
            Toast.makeText(requireContext(), "Item clicked: ${clickedItem.text}", Toast.LENGTH_SHORT).show()

            // Add any other actions i want to perform on item click
        }

        recyclerView.adapter = adapterss



        return view
    }
}