package com.android.soundlyspotify.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.AddSong

class AddMySongAdapter(private val dataList: List<AddSong>) :
    RecyclerView.Adapter<AddMySongAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.addimageMV)
        val textViewSongName: TextView = itemView.findViewById(R.id.addsongNameTextView)
        val textViewArtist: TextView = itemView.findViewById(R.id.addartistTextView)
        val textViewTiming: TextView = itemView.findViewById(R.id.addSongDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.add_song_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textViewSongName.text = currentItem.songName
        holder.textViewArtist.text = currentItem.artist
        holder.textViewTiming.text = currentItem.timing
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
