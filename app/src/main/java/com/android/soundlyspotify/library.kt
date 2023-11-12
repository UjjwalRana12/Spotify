package com.android.soundlyspotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.GridAdapter
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.GridItemData

class library : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_library, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewgridlibrary)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        val data = listOf(
            GridItemData("Item 1", R.drawable.photochaar),
            GridItemData("Item 2", R.drawable.photoek),
            GridItemData("Item 3", R.drawable.photoek),
            GridItemData("Item 4", R.drawable.photoek),
            GridItemData("Item 5", R.drawable.photoek),
            // Add more items as needed
        )

        val adapter = GridAdapter(data)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String) =
            library().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
