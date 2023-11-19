package com.android.soundlyspotify

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity4 : AppCompatActivity() {

    private val selectedCardViews = HashSet<CardView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val cardView1: CardView = findViewById(R.id.CardView3as)
        val cardView2: CardView = findViewById(R.id.CardView4as)
        val cardView3: CardView = findViewById(R.id.cardView5as)
        val cardView4: CardView = findViewById(R.id.cardview6as)
        val cardView5: CardView = findViewById(R.id.cardView1as)
        val cardView6: CardView = findViewById(R.id.CardView2as)

        // Set click listeners for each CardView
        cardView1.setOnClickListener { toggleCardSelection(cardView1) }
        cardView2.setOnClickListener { toggleCardSelection(cardView2) }
        cardView3.setOnClickListener { toggleCardSelection(cardView3) }
        cardView4.setOnClickListener { toggleCardSelection(cardView4) }
        cardView5.setOnClickListener { toggleCardSelection(cardView5) }
        cardView6.setOnClickListener { toggleCardSelection(cardView6) }
    }

    private fun toggleCardSelection(clickedCardView: CardView) {
        // Toggle the selection of the clicked CardView
        if (selectedCardViews.contains(clickedCardView)) {
            // CardView is already selected, deselect it
            clickedCardView.setCardBackgroundColor(Color.TRANSPARENT)
            selectedCardViews.remove(clickedCardView)
        } else {
            // CardView is not selected, select it
            clickedCardView.setCardBackgroundColor(Color.parseColor("#008B8B"))
            selectedCardViews.add(clickedCardView)
        }
    }
}
