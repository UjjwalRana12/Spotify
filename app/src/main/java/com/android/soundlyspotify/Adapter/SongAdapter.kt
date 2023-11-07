package com.android.soundlyspotify.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.data.Song

class SongAdapter(private val dataList: List<Song>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_view, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageMV: ImageView = itemView.findViewById(R.id.imageMV)
        private val songName: TextView = itemView.findViewById(R.id.songNameTextView)
        private val artist: TextView = itemView.findViewById(R.id.artistTextView)
        private val songDuration: TextView = itemView.findViewById(R.id.SongDuration)

        fun bind(item: Song) {
            imageMV.setImageResource(item.imageMV)
            songName.text = item.songName
            artist.text = item.artist
            songDuration.text = item.duration
        }

    }
}
