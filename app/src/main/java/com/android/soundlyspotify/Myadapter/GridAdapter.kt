package com.android.soundlyspotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.models.GridItemData
import com.bumptech.glide.Glide

class GridAdapter(
    private val data: List<GridItemData>,
    private val onImageClickListener: (GridItemData) -> Unit
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_grid_library_layout, parent, false)
        return GridViewHolder(view, onImageClickListener)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class GridViewHolder(
        itemView: View,
        private val onImageClickListener: (GridItemData) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewlib3)
        private val textView: TextView = itemView.findViewById(R.id.textViewlib21)

        init {
            imageView.setOnClickListener {
                onImageClickListener.invoke(data[adapterPosition])
            }
        }

        fun bind(itemData: GridItemData) {
            Glide.with(itemView)
                .load(itemData.imageUrl)
                .placeholder(R.drawable.desikalakar)
                .error(R.drawable.desikalakar)
                .into(imageView)

            textView.text = itemData.text
        }
    }
}
