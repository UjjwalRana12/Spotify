package com.android.soundlyspotify.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.applied_api.SongModel
import com.android.soundlyspotify.models.MyItem
import com.bumptech.glide.Glide

class MusicAdapter(
    private var itemList: MutableList<MyItem>,
    private val onItemClick: (MyItem) -> Unit
) : RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.musico_layout, parent, false)
        return MyViewHolder(itemView)
    }

    fun updateData(newItemList: List<MyItem>) {
        itemList.clear()
        itemList.addAll(newItemList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList.getOrNull(position)

        currentItem?.let {
            Glide.with(holder.itemView.context)
                .load(it.imageUrl)
                .override(450, 450)
                .centerCrop()
                .into(holder.imageViewItem)

            holder.textViewItem.text = it.text

            holder.itemView.setOnClickListener { _ ->
                onItemClick(it)
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewItem: ImageView = itemView.findViewById(R.id.lastadaptimageview)
        val textViewItem: TextView = itemView.findViewById(R.id.lastadaptTV)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
