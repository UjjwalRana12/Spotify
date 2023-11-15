package com.android.soundlyspotify
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.soundlyspotify.R

class Game : Fragment() {

    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        questionTextView = view.findViewById(R.id.QuestiontextView)
        option1Button = view.findViewById(R.id.option1Button)
        option2Button = view.findViewById(R.id.option2Button)

        return view
    }

    fun onOptionSelected(view: View) {
        // Reset background color for both options
        option1Button.setBackgroundColor(Color.TRANSPARENT)
        option2Button.setBackgroundColor(Color.TRANSPARENT)

        // Highlight the selected option
        val selectedOption = view as Button
        selectedOption.setBackgroundColor(Color.YELLOW)

        // Handle the selected option (you may want to do something specific here)
        val selectedOptionText = selectedOption.text.toString()
        // Do something with the selected option text, e.g., check if it's the correct answer.
    }
}
