package com.android.soundlyspotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.Myadapter.BestSellerAdapter
import com.android.soundlyspotify.models.BestSeller

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        // Initialize RecyclerView
        val bestsellerRecyclerView: RecyclerView = view.findViewById(R.id.bestSellerRecyclerView)
        bestsellerRecyclerView.setHasFixedSize(true)
        bestsellerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // Sample data for testing
        val bestSellers = listOf(
            BestSeller(R.drawable.photoek, "Title 1"),
            BestSeller(R.drawable.photodo, "Title 2"),
            BestSeller(R.drawable.phototeen, "Title 3"),
            BestSeller(R.drawable.photochaar, "Title 4"),
            BestSeller(R.drawable.photopaanch, "Title 5")
            // Add more items as needed
        )

        // Set up BestSellerAdapter and attach it to the RecyclerView
        val bestSellerAdapter = BestSellerAdapter(bestSellers)
        bestsellerRecyclerView.adapter = bestSellerAdapter

        return view
    }

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }
}
