package com.android.soundlyspotify

import android.content.Context
import android.media.MediaPlayer

class MediaPlayerManager(private val context: Context, private val songUrl: String) {

    private val mediaPlayer: MediaPlayer = MediaPlayer()

    init {
        mediaPlayer.setDataSource(songUrl)
        mediaPlayer.setOnPreparedListener {
            // Handle preparation, e.g., setting up SeekBar max value
            // You can also start playback here if needed
        }

        mediaPlayer.setOnCompletionListener {
            // Handle completion
        }

        // Prepare the MediaPlayer asynchronously
        mediaPlayer.prepareAsync()
    }

    fun playPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer.seekTo(position)
    }

    fun release() {
        mediaPlayer.release()
    }

    // Example of checking if the MediaPlayer is currently playing
    fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }
}
