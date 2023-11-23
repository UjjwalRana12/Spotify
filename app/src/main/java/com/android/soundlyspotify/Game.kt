package com.android.soundlyspotify

import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Game : Fragment() {

    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button

    private lateinit var gameData: GameData
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var gameApiService: GameApiService // Declare gameApiService here

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://test-mkcw.onrender.com/api/game/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("GameFragment", "onCreateView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        questionTextView = view.findViewById(R.id.QuestiontextView)
        option1Button = view.findViewById(R.id.option1Button)
        option2Button = view.findViewById(R.id.option2Button)

        // Initialize gameApiService
        gameApiService = retrofit.create(GameApiService::class.java)

        // Call the API to get game data
        fetchGameData()

        return view
    }

    private fun fetchGameData() {
        Log.d("GameFragment", "fetchGameData")
        val call = gameApiService.getGameData()

        call.enqueue(object : Callback<GameData> {
            override fun onResponse(call: Call<GameData>, response: Response<GameData>) {
                if (response.isSuccessful) {
                    gameData = response.body()!!
                    updateUI()
                    println("GameData updated")
                    playAudio(gameData.data.audio_1)
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<GameData>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch game data", t)
                // Handle network errors
                println("GameData failed")
            }
        })
    }

    private fun updateUI() {
       // questionTextView.text = gameData.data.name
        option1Button.text = gameData.data.option1
        option2Button.text = gameData.data.option2
    }

    private fun playAudio(audioUrl: String) {
        mediaPlayer?.release() // Release any existing MediaPlayer resources

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                start()
            }
            setOnErrorListener { _, _, _ ->
                Log.e("AUDIO_PLAYBACK", "Error during audio playback")
                false
            }
        }
    }

    fun onOptionSelected(view: View) {
        // Reset background color for both options
        option1Button.setBackgroundColor(Color.TRANSPARENT)
        option2Button.setBackgroundColor(Color.TRANSPARENT)

        // Highlight the selected option
        val selectedOption = view as Button
        selectedOption.setBackgroundColor(Color.YELLOW)

        // Handle the selected option
        val selectedOptionText = selectedOption.text.toString()

        // Call the function to post the selected option
        postSelectedOption(selectedOptionText)
    }

    private fun postSelectedOption(selectedOptionText: String) {
        // Create a map containing the data you want to send in the POST request
        val postData = mapOf("selectedOption" to selectedOptionText)

        // Make the POST request
        val call = gameApiService.postSelectedOption(postData)
        call.enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    val gameResponse = response.body()

                    if (gameResponse?.success == true) {
                        // Handle the correct answer
                        val message = gameResponse.message
                        showToast(message)
                    } else {
                        // Handle the incorrect answer or other error
                        showToast("Incorrect answer or API error")
                    }
                } else {
                    // Handle an unsuccessful response
                    showToast("API request failed")
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                // Handle network errors
                showToast("Network error")
            }
        })
    }

    private fun showToast(message: String) {
        showToast("the error message is : ${message}")
        // Use your preferred method to display a message to the user
        // For example, Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release MediaPlayer resources when the fragment is destroyed
    }
}
