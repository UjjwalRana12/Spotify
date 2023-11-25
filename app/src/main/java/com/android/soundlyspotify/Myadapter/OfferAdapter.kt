package com.android.soundlyspotify.Myadapter

import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            // Notify the external click listener
//            Log.d(
//                "OfferAdapter",
//                "Item clicked: ID: ${currentOffer.id}, Title: ${currentOffer.title}, Query: ${currentOffer.query}"
//            )

            Log.d("OfferAdapter", "Item clicked: Title: ${currentOffer.title}, Query: ${currentOffer.query}")
            itemClickListener?.onItemClick(currentOffer)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val offerImage: ImageView = itemView.findViewById(R.id.offerMv)
        private val offerTitle: TextView = itemView.findViewById(R.id.offerTv)

        fun bind(offer: Offer) {
            // Handle null image resource
            if (offer.image != null) {
                offerImage.setImageResource(offer.image)
            } else {
                offerImage.setImageResource(R.drawable.defaultimage) // Provide a default image resource
            }

            // Handle null title
            offerTitle.text = offer.title ?: "Default Title"
        }
    }

    interface OnItemClickListener {
        fun onItemClick(offer: Offer)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

    fun updateOffers(offers: List<Offer>) {
        GlobalScope.launch(Dispatchers.Main) {
            submitList(offers)
        }
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
