package com.android.soundlyspotify.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.Song

import com.squareup.picasso.Picasso

class BestSellerAdapter(private var songs: List<Song>) : RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder>() {

    // Update function to set new data in the adapter
    fun setSongs(newSongs: List<Song>?) {
        songs = newSongs ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_seller_layout, parent, false)
        return BestSellerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        val currentSong = songs[position]
        holder.bind(currentSong)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    inner class BestSellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bestSellerImage: ImageView = itemView.findViewById(R.id.bestsellerMV)
        private val bestSellerTitle: TextView = itemView.findViewById(R.id.bestsellerTV)

        fun bind(song: Song) {
            // Load image using Picasso (or you can use Glide, or any other image loading library)
            if (!song.thumbnail_url.isNullOrEmpty()) {
                Picasso.get().load(song.thumbnail_url).into(bestSellerImage)
            } else {
                // You can set a placeholder image or handle the case when the thumbnail URL is empty
                bestSellerImage.setImageResource(R.drawable.photoek)
            }

            bestSellerTitle.text = song.name
        }
    }
}
