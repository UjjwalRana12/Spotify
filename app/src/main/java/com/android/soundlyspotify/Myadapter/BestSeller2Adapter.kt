package com.android.soundlyspotify.Myadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.BestSeller2

class BestSeller2Adapter(private val context: Context, private val bestSellers: List<BestSeller2>) :
    RecyclerView.Adapter<BestSeller2Adapter.BestSeller2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSeller2ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.bestseller2_layout, parent, false)
        return BestSeller2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestSeller2ViewHolder, position: Int) {
        val currentItem = bestSellers[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return bestSellers.size
    }

    inner class BestSeller2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.bestseller2Mv)
        private val titleTextView: TextView = itemView.findViewById(R.id.bestseller2Tv)

        fun bind(bestSeller: BestSeller2) {
            imageView.setImageResource(bestSeller.image)
            titleTextView.text = bestSeller.title
        }
    }
}
