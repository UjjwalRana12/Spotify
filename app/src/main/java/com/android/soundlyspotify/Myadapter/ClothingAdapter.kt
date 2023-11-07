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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clothing_layout, parent, false)
        return ClothingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClothingViewHolder, position: Int) {
        val currentItem = clothingList[position]

        holder.clothingImage.setImageResource(currentItem.image)
        holder.clothingTitle.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return clothingList.size
    }

    inner class ClothingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clothingImage: ImageView = itemView.findViewById(R.id.clothingMv)
        val clothingTitle: TextView = itemView.findViewById(R.id.clothingTv)
    }
}
