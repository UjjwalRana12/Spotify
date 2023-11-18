package com.android.soundlyspotify

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

interface MediaPlayerManagerListener {
    fun onPrepared()
    fun onCompletion()
    fun onError(error: String)
}

class MediaPlayerManager(private val context: Context, private val songUrl: String) {

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var listener: MediaPlayerManagerListener? = null
    private var isPrepared: Boolean = false

    fun setListener(listener: MediaPlayerManagerListener) {
        this.listener = listener
    }

    init {
        try {
            mediaPlayer.setDataSource(songUrl)
            mediaPlayer.setOnPreparedListener {
                isPrepared = true
                listener?.onPrepared()
            }
            mediaPlayer.setOnCompletionListener {
                listener?.onCompletion()
            }
            mediaPlayer.setOnErrorListener { _, what, extra ->
                Log.e("MediaPlayerManager", "Error: what=$what, extra=$extra")
                listener?.onError("MediaPlayer error: what=$what, extra=$extra")
                false
            }
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            listener?.onError("Error initializing MediaPlayer: ${e.message}")
            e.printStackTrace()
        }
    }

    fun playPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            if (isPrepared) {
                mediaPlayer.start()
            } else {
                // Handle the case when start is called in an invalid state
                listener?.onError("Error: Start called in an invalid state")
            }
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer.seekTo(position)
    }

    fun release() {
        mediaPlayer.release()
    }

    fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    // Ensure the MediaPlayer is prepared before calling start
    fun start() {
        if (isPrepared) {
            mediaPlayer.start()
        } else {
            // Handle the case when start is called in an invalid state
            listener?.onError("Error: Start called in an invalid state")
        }
    }
}
