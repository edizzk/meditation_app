package com.example.meditation_app.service

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.meditation_app.utils.UiState
import com.example.meditation_app.utils.mediaPlayerUrl

class MediaPlayerService {

    fun prepareMediaPlayer(result: (UiState<MediaPlayer?>) -> Unit) {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer.setDataSource(mediaPlayerUrl)
            mediaPlayer.prepare()
            result.invoke(UiState.Success(mediaPlayer))
        } catch (exception: Exception){
            result.invoke(UiState.Failure(exception.message))
        }
    }

}