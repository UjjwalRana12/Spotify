package com.android.soundlyspotify
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.android.soundlyspotify.MediaPlayerManager
import com.android.soundlyspotify.R
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class playerfragment : Fragment() {

    private lateinit var mediaPlayerManager: MediaPlayerManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Access MainActivity2 and hide the BottomNavigationView
        (activity as? MainActivity2)?.hideBottomNavigationView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playerfragment, container, false)

        val playPauseButton = view.findViewById<AppCompatImageView>(R.id.imageplaypause)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)

        // Set up click listeners and other necessary UI setup
        playPauseButton.setOnClickListener {
            mediaPlayerManager.playPause()
            // Update UI as needed
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayerManager.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do something when user starts touching the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do something when user stops touching the SeekBar
            }
        })

        // Initialize MediaPlayerManager
        mediaPlayerManager = MediaPlayerManager(requireContext(), R.raw.media1)

        return view
    }

    override fun onDestroyView() {
        // Show the BottomNavigationView when leaving the fragment
        (activity as? MainActivity2)?.showBottomNavigationView()

        super.onDestroyView()
    }

    override fun onDestroy() {
        mediaPlayerManager.release()
        super.onDestroy()
    }
}
