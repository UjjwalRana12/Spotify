package com.android.soundlyspotify

import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
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
    private lateinit var audioManager: AudioManager

    private val afChangeListener = AudioManager.OnAudioFocusChangeListener {}

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://test-mkcw.onrender.com/game/")
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

        // Initialize AudioManager
        audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Call the API to get game data
        fetchGameData()

        return view
    }

    private fun fetchGameData() {
        Log.d("GameFragment", "gamedata fetch ho gya")
        val call = gameApiService.getGameData()
        println("GameFragment api services begin")
        call.enqueue(object : Callback<GameData> {
            override fun onResponse(call: Call<GameData>, response: Response<GameData>) {
                if (response.isSuccessful) {
                    println("Game api response is success")
                    gameData = response.body()!!
                    updateUI()
                    println("GameData updated")
                    Log.d("AUDIO_PLAYBACK", "Audio URL: ${gameData.data.audio_1}")
                    playAudio(gameData.data.audio_1)
                } else {
                    when (response.code()) {
                        401 -> {
                            // Unauthorized - Handle accordingly
                            showToast("Unauthorized access")
                        }

                        404 -> {
                            // Not Found - Handle accordingly
                            showToast("Game data not found")
                        }

                        500 -> {
                            // Internal Server Error - Handle accordingly
                            showToast("Internal server error")
                        }

                        else -> {
                            // Generic error - Handle accordingly
                            showToast("Failed to fetch game data. Error code: ${response.code()}")
                        }
                    }
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
        Log.d("AUDIO_PLAYBACK", "Audio URL: $audioUrl")
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                if (requestAudioFocus()) {
                    start()
                }
            }
            setOnErrorListener { _, what, extra ->
                Log.e("AUDIO_PLAYBACK", "Error during audio playback. Error: $what, Extra: $extra")
                releaseAudioFocus()
                false
            }
        }
    }

    private fun requestAudioFocus(): Boolean {
        val result = audioManager.requestAudioFocus(
            afChangeListener,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
        Log.d("AUDIO_PLAYBACK", "Audio focus request result: $result")

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    private fun releaseAudioFocus() {
        audioManager.abandonAudioFocus(afChangeListener)
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
        // Use your preferred method to display a message to the user
        // For example, Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release MediaPlayer resources when the fragment is destroyed
    }
}
