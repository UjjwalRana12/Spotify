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
    private lateinit var playPauseButton: AppCompatImageView
    private lateinit var seekBar: SeekBar
    private var songUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playerfragment, container, false)

        playPauseButton = view.findViewById(R.id.imageplaypause)
        seekBar = view.findViewById(R.id.seekBar)

        // Access the song URL from arguments
        songUrl = arguments?.getString(ARG_SONG_URL)

        // Initialize MediaPlayerManager with the song URL
        mediaPlayerManager = songUrl?.let { MediaPlayerManager(requireContext(), it) }
            ?: throw IllegalStateException("Song URL is null")

        // Set up click listeners and other necessary UI setup
        playPauseButton.setOnClickListener {
            mediaPlayerManager.playPause()
            updatePlayPauseButton()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayerManager.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do something when the user starts touching the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do something when the user stops touching the SeekBar
            }
        })

        // Hide the BottomNavigationView
        (activity as? MainActivity2)?.hideBottomNavigationView()

        // Set up Bottom Sheet
        val bottomSheetButton = view.findViewById<AppCompatImageView>(R.id.imageViewbottom18)
        bottomSheetButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

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

    private fun updatePlayPauseButton() {
        // Update play/pause button UI based on the current playback state
        val iconResource =
            if (mediaPlayerManager.isPlaying()) R.drawable.playarrow else R.drawable.baseline_pause_circle_24
        playPauseButton.setImageResource(iconResource)
    }

    companion object {
        private const val ARG_SONG_URL = "arg_song_url"

        fun createInstance(songUrl: String): playerfragment {
            val fragment = playerfragment()
            val args = Bundle()
            args.putString(ARG_SONG_URL, songUrl)
            fragment.arguments = args
            return fragment
        }
    }
}