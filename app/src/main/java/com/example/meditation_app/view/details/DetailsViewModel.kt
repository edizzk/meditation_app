package com.example.meditation_app.view.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.service.MediaPlayerService
import com.example.meditation_app.utils.Resource
import com.example.meditation_app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mediaPlayerService: MediaPlayerService
): BaseViewModel() {

    private val _mediaPlayer = MutableLiveData(UiState(isLoading = true))
    val mediaPlayer: LiveData<UiState>
        get() = _mediaPlayer

    fun prepareMediaPlayer() = viewModelScope.launch {
        mediaPlayerService.prepareMediaPlayer { state ->
            _mediaPlayer.postValue(UiState(data = state, isLoading = false))
            when(state) {
                is Resource.Success -> Log.d(TAG, "playAudio() Success: ${state.data}")
                is Resource.Failure -> Log.d(TAG, "playAudio() Failure: ${state.error}")
            }
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel: "
    }

}
