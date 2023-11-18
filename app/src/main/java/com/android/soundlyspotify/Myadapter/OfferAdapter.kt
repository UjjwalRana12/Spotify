package com.android.soundlyspotify.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.soundlyspotify.R
import com.android.soundlyspotify.models.Offer

class OfferAdapter(private val offers: List<Offer>, private val onItemClick: (Offer) -> Unit) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_layout, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val currentOffer = offers[position]
        holder.bind(currentOffer)

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            onItemClick(currentOffer)
        }
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val offerImage: ImageView = itemView.findViewById(R.id.offerMv)
        private val offerTitle: TextView = itemView.findViewById(R.id.offerTv)

        fun bind(offer: Offer) {
            offerImage.setImageResource(offer.image)
            offerTitle.text = offer.title
        }
    }
}