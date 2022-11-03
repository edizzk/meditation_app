package com.example.meditation_app.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.data.model.User
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.data.repository.MeditationsRepository
import com.example.meditation_app.data.repository.StoriesRepository
import com.example.meditation_app.utils.Resource
import com.example.meditation_app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val medRepository: MeditationsRepository,
    private val storyRepository: StoriesRepository,
    private val authRepository: AuthRepository
): BaseViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?>
        get() = _currentUser

    private val _stateMed = MutableLiveData(UiState(isLoading = true))
    val stateMed: LiveData<UiState>
        get() = _stateMed

    private val _stateStory = MutableLiveData(UiState(isLoading = true))
    val stateStory: LiveData<UiState>
        get() = _stateStory

    init {
        getCurrentUser()
        getAllMeditations()
        getAllStories()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        authRepository.getCurrentUser { state ->
            when(state) {
                is Resource.Success -> _currentUser.postValue(state.data)
                is Resource.Failure -> Log.d(TAG, "getCurrentUser Failure: ${state.error}")
            }
        }
    }

    private fun getAllMeditations() = viewModelScope.launch {
        medRepository.getAllMeditations { state ->
            _stateMed.postValue(UiState(data = state, isLoading = false))
            when(state) {
                is Resource.Success -> Log.d(TAG, "getAllMeditations Success: ${state.data.size}")
                is Resource.Failure -> Log.d(TAG, "getAllMeditations Failure: ${state.error}")
            }
        }
    }

    private fun getAllStories() = viewModelScope.launch {
        storyRepository.getAllStories { state ->
            _stateStory.postValue(UiState(data = state, isLoading = false))
            when(state) {
                is Resource.Success -> Log.d(TAG, "getAllStories Success: ${state.data.size}")
                is Resource.Failure -> Log.d(TAG, "getAllStories Failure: ${state.error}")
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel: "
    }

}