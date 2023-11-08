package com.android.soundlyspotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val dataList: List<SongModel>) :
    RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageMV)
        private val textViewTitle: TextView = itemView.findViewById(R.id.songNameTextView)
        private val textViewArtist: TextView = itemView.findViewById(R.id.artistTextView)
        private val textViewAlbum: TextView = itemView.findViewById(R.id.SongDuration)

        fun bind(item: SongModel) {
            imageView.setImageResource(item.image)
            textViewTitle.text = item.title
            textViewArtist.text = item.artist
            textViewAlbum.text = item.album
        }
    }
}

data class SongModel(val image: Int, val title: String, val artist: String, val album: String)
