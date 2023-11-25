package com.android.soundlyspotify.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.BestSeller
import com.squareup.picasso.Picasso

class BestSellerAdapter(private var bestSellers: List<BestSeller>) :
    RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder>() {

    // Define a listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int, query: String)
    }

    // Declare a listener variable
    private var onItemClickListener: OnItemClickListener? = null

    // Setter method for the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    // Update the dataset with new data
    fun updateData(newBestSellers: List<BestSeller>) {
        bestSellers = newBestSellers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.best_seller_layout, parent, false)
        return BestSellerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        val currentBestSeller = bestSellers[position]
        holder.bind(currentBestSeller)

        // Set click listener for the entire item view
        holder.itemView.setOnClickListener {
            // Notify the listener when an item is clicked
            onItemClickListener?.onItemClick(position, currentBestSeller.query)
        }
    }

    override fun getItemCount(): Int {
        return bestSellers.size
    }

    inner class BestSellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bestSellerImage: ImageView = itemView.findViewById(R.id.bestsellerMV)
        private val bestSellerTitle: TextView = itemView.findViewById(R.id.bestsellerTV)

        fun bind(bestSeller: BestSeller) {
            bestSellerImage.setImageResource(bestSeller.image)
            bestSellerTitle.text = bestSeller.title

            Picasso.get()
                .load(bestSeller.image)
                .resize(450, 450) // Set your desired width and height
                .centerCrop()
                .into(bestSellerImage)

            bestSellerTitle.text = bestSeller.title
        }
    }
}
