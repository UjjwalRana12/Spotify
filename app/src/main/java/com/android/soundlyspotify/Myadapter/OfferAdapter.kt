package com.android.soundlyspotify.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.Offer

class OfferAdapter(
    private val onQueryRequested: (String) -> Unit
) : ListAdapter<Offer, OfferAdapter.OfferViewHolder>(OfferDiffCallback()) {

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_layout, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val currentOffer = getItem(position)
        holder.bind(currentOffer)

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            // Call the onQueryRequested callback with the query property
            onQueryRequested(currentOffer.query)

            // Notify the external click listener
            itemClickListener?.onItemClick(currentOffer)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val offerImage: ImageView = itemView.findViewById(R.id.offerMv)
        private val offerTitle: TextView = itemView.findViewById(R.id.offerTv)

        fun bind(offer: Offer) {
            offerImage.setImageResource(offer.image)
            offerTitle.text = offer.title
        }
    }

    interface OnItemClickListener {
        fun onItemClick(offer: Offer)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    private class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.query == newItem.query // Assuming query is unique
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }
    }
}
