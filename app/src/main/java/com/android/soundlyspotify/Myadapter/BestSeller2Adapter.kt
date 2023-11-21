package com.android.soundlyspotify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.BestSeller2
import com.bumptech.glide.Glide

class BestSeller2Adapter(
    private val context: Context,
    private val bestSellers: List<BestSeller2>
) : RecyclerView.Adapter<BestSeller2Adapter.BestSeller2ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: BestSeller2)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSeller2ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.bestseller2_layout, parent, false)
        return BestSeller2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestSeller2ViewHolder, position: Int) {
        val currentBestSeller = bestSellers[position]
        holder.bind(currentBestSeller)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentBestSeller)
        }
    }

    override fun getItemCount(): Int {
        return bestSellers.size
    }

    inner class BestSeller2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.bestseller2Mv)
        private val titleTextView: TextView = itemView.findViewById(R.id.bestseller2Tv)

        fun bind(bestSeller: BestSeller2) {
            // Load image using Glide
            Glide.with(context)
                .load(bestSeller.image)
                .override(450, 450)
                .placeholder(R.drawable.defaultimage) // Placeholder image while loading
                .error(R.drawable.defaultimage) // Image to show in case of error
                .into(imageView)

            // Set other data
            titleTextView.text = bestSeller.title
        }
    }
}