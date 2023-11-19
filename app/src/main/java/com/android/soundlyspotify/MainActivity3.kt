package com.android.soundlyspotify

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {

    private val selectedTextViews = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val text1: TextView = findViewById(R.id.textView35)
        val text2: TextView = findViewById(R.id.textView36)
        val text3: TextView = findViewById(R.id.textView37)
        val text4: TextView = findViewById(R.id.textView38)

        // Set click listeners for each TextView
        text1.setOnClickListener { toggleColor(text1) }
        text2.setOnClickListener { toggleColor(text2) }
        text3.setOnClickListener { toggleColor(text3) }
        text4.setOnClickListener { toggleColor(text4) }
    }

    private fun toggleColor(clickedTextView: TextView) {
        // Toggle the background color to the specified color when clicked
        if (selectedTextViews.contains(clickedTextView)) {
            // If already selected, remove it
            selectedTextViews.remove(clickedTextView)
            clickedTextView.setBackgroundColor(Color.TRANSPARENT)
            clickedTextView.setTextColor(Color.BLACK)
        } else {
            // If not selected, add it
            selectedTextViews.add(clickedTextView)
            clickedTextView.setBackgroundColor(Color.parseColor("#008B8B"))
            clickedTextView.setTextColor(Color.WHITE)
        }
    }
}
