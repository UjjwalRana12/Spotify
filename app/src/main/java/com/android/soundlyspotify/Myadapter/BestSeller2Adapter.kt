package com.android.soundlyspotify
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.data.SongDisplayed

import com.bumptech.glide.Glide

class BestSeller2Adapter(
private val context: Context,
private val songs: List<SongDisplayed>
) : RecyclerView.Adapter<BestSeller2Adapter.BestSeller2ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: SongDisplayed)
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
        val currentItem = songs[position]
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    inner class BestSeller2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.bestseller2Mv)
        private val titleTextView: TextView = itemView.findViewById(R.id.bestseller2Tv)

        fun bind(song: SongDisplayed) {
            // Load image using Glide
            Glide.with(context)
                .load(song.thumbnailUrl)
                .placeholder(R.drawable.defaultimage) // Placeholder image while loading
                .error(R.drawable.defaultimage) // Image to show in case of error
                .into(imageView)

            // Set other data
            titleTextView.text = song.name
        }
    }
}
