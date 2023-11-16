package com.android.soundlyspotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.applied_api.SongModel

class SongAdapter(private var dataList: MutableList<SongModel>) :
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

    // This method updates the dataset and refreshes the RecyclerView
    fun setData(newData: MutableList<SongModel>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    // Additional method to add a single song to the dataset
    fun addSong(song: SongModel) {
        dataList.add(song)
        notifyItemInserted(dataList.size - 1)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageMV)
        private val textViewTitle: TextView = itemView.findViewById(R.id.songNameTextView)
        private val textViewArtist: TextView = itemView.findViewById(R.id.artistTextView)
        private val textViewDuration: TextView = itemView.findViewById(R.id.SongDuration)
        private val playpause: ImageView = itemView.findViewById(R.id.imageView3play)
        fun bind(item: SongModel) {
            imageView.setImageResource(item.imageMV)
            textViewTitle.text = item.name
            textViewArtist.text = item.artist
            textViewDuration.text = item.song_duration


            // You can further bind other properties as needed
        }
    }
}