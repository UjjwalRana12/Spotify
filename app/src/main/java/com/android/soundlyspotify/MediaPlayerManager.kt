package com.android.soundlyspotify

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes



class MediaPlayerManager(private val context: Context, @RawRes rawResourceId: Int) {

    private val mediaPlayer: MediaPlayer = MediaPlayer.create(context, rawResourceId)

    init {
        mediaPlayer.setOnPreparedListener {
            // Handle preparation, e.g., setting up SeekBar max value
        }

        mediaPlayer.setOnCompletionListener {
            // Handle completion
        }
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


}