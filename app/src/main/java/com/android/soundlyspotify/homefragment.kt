package com.android.soundlyspotify

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.Myadapter.BestSeller2Adapter
import com.android.soundlyspotify.Myadapter.BestSellerAdapter
import com.android.soundlyspotify.Myadapter.ClothingAdapter
import com.android.soundlyspotify.Myadapter.OfferAdapter
import com.android.soundlyspotify.models.BestSeller
import com.android.soundlyspotify.models.BestSeller2
import com.android.soundlyspotify.models.Clothing
import com.android.soundlyspotify.models.Offer

class homefragment : Fragment() {

    private lateinit var offerRecyclerView: RecyclerView
    private lateinit var bestsellerRecyclerView: RecyclerView
    private lateinit var clothingRecyclerView: RecyclerView
    private lateinit var bestseller2RecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)

        // Initialize RecyclerViews and set their properties
        offerRecyclerView = view.findViewById(R.id.offerRecyclerView)
        offerRecyclerView.setHasFixedSize(true)
        offerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val offers = listOf(
            Offer(R.drawable.honeysingh, "Offer Title 1"),
            Offer(R.drawable.honeysingh, "Offer Title 2"),
            Offer(R.drawable.honeysingh, "Offer Title 3"),
            // Add more items as needed
        )
        val offerAdapter = OfferAdapter(offers)
        offerRecyclerView.adapter = offerAdapter




        // BESTSELLER IS HERE
        bestsellerRecyclerView = view.findViewById(R.id.bestSellerRecyclerView)
        bestsellerRecyclerView.setHasFixedSize(true)
        bestsellerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val bestSellers = listOf(
            BestSeller(R.drawable.honeysingh, "Title 1"),
            BestSeller(R.drawable.honeysingh, "Title 2"),
            BestSeller(R.drawable.honeysingh, "Title 3"),
            // or item add krlo agar krna ho toh


            // Assuming you have a list of BestSeller objects named bestSellers
            // val bestSellers: List<BestSeller> = // Populate this with your data
            //this will help me to put api
        )

        // setting up bestseller adapter
        val bestSellerAdapter = BestSellerAdapter(bestSellers)
        bestsellerRecyclerView.adapter = bestSellerAdapter







        // clothing is here



        clothingRecyclerView = view.findViewById(R.id.clothingRecyclerView)
        clothingRecyclerView.setHasFixedSize(true)
        clothingRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val context: Context = requireContext()// suggested by chatgpt to encounter errors
        val clothingList = listOf(
            Clothing(R.drawable.honeysingh, "Summer "),
            Clothing(R.drawable.honeysingh, "Casual "),
            Clothing(R.drawable.honeysingh, "Formal "),
            // Add more  items as needed
        )

        val clothingAdapter = ClothingAdapter(context,clothingList)
        clothingRecyclerView.adapter = clothingAdapter







        bestseller2RecyclerView = view.findViewById(R.id.bestSeller2RecyclerView)
        bestseller2RecyclerView.setHasFixedSize(true)
        bestseller2RecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val bestSellersList = listOf(
            BestSeller2(R.drawable.arjitsingh, "arjit singh"),
            BestSeller2(R.drawable.arjitsingh, "arjit Awaits"),
            BestSeller2(R.drawable.arjitsingh, "arjit"),
            // Add item
        )
        val adapter = BestSeller2Adapter(context, bestSellersList)
        bestseller2RecyclerView.adapter = adapter






        return view
    }
}
