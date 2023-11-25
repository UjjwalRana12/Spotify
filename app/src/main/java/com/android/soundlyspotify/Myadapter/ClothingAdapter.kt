package com.android.soundlyspotify.Myadapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.Clothing
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClothingAdapter(
    private val context: Context,
    private var itemClickListener: OnItemClickListener? = null
) : ListAdapter<Clothing, ClothingAdapter.ClothingViewHolder>(ClothingDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(query: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    fun updateList(newList: List<Clothing>) {
        submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clothing_layout, parent, false)
        return ClothingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClothingViewHolder, position: Int) {
        val currentItem = getItem(position)

        // Load image using Picasso
        Picasso.get()
            .load(currentItem.image)
            .resize(450, 450)
            .centerCrop()
            .into(holder.clothingImage)

        holder.clothingTitle.text = currentItem.title

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            currentItem.query?.let { query ->
                // Notify the listener when an item is clicked
                itemClickListener?.onItemClick(query)
            }
        }
    }

    inner class ClothingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clothingImage: ImageView = itemView.findViewById(R.id.clothingMv)
        val clothingTitle: TextView = itemView.findViewById(R.id.clothingTv)
    }

    private class ClothingDiffCallback : DiffUtil.ItemCallback<Clothing>() {
        override fun areItemsTheSame(oldItem: Clothing, newItem: Clothing): Boolean {
            return oldItem.query == newItem.query
        }

        override fun areContentsTheSame(oldItem: Clothing, newItem: Clothing): Boolean {
            return oldItem == newItem
        }
    }
}

