package com.example.meditation_app.view.details

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.service.MediaPlayerService
import com.example.meditation_app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mediaPlayerService: MediaPlayerService
): BaseViewModel() {

    private val _mediaPlayer = MutableLiveData<MediaPlayer?>()
    val mediaPlayer: LiveData<MediaPlayer?>
        get() = _mediaPlayer

    init {
        prepareMediaPlayer()
    }

    private fun prepareMediaPlayer() = viewModelScope.launch {
        mediaPlayerService.prepareMediaPlayer { state ->
            when(state) {
                is UiState.Success -> _mediaPlayer.postValue(state.data)
                is UiState.Failure -> Log.d(TAG, "playAudio() Failure: ${state.error}")
            }
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel: "
    }

}
