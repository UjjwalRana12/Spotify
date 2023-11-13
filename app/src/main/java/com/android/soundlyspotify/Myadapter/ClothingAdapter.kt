package com.android.soundlyspotify.Myadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.Clothing

class ClothingAdapter(private val context: Context, private val clothingList: List<Clothing>) :
    RecyclerView.Adapter<ClothingAdapter.ClothingViewHolder>() {

    // Define a listener interface
    interface OnItemClickListener {
        fun onItemClick(item: Clothing)
    }

    private var itemClickListener: OnItemClickListener? = null

    // Set the click listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clothing_layout, parent, false)
        return ClothingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClothingViewHolder, position: Int) {
        val currentItem = clothingList[position]

        holder.clothingImage.setImageResource(currentItem.image)
        holder.clothingTitle.text = currentItem.title

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            // Notify the listener when an item is clicked
            itemClickListener?.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return clothingList.size
    }

    inner class ClothingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clothingImage: ImageView = itemView.findViewById(R.id.clothingMv)
        val clothingTitle: TextView = itemView.findViewById(R.id.clothingTv)
    }
}
