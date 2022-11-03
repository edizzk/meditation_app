package com.example.meditation_app.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.meditation_app.base.BaseViewModel
import com.example.meditation_app.data.model.Meditations
import com.example.meditation_app.data.model.Stories
import com.example.meditation_app.data.model.User
import com.example.meditation_app.data.repository.AuthRepository
import com.example.meditation_app.data.repository.MeditationsRepository
import com.example.meditation_app.data.repository.StoriesRepository
import com.example.meditation_app.utils.Resource
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

    private val _responseMed = MutableLiveData<List<Meditations>?>()
    val responseMed: LiveData<List<Meditations>?>
        get() = _responseMed

    private val _responseStory = MutableLiveData<List<Stories>?>()
    val responseStory: LiveData<List<Stories>?>
        get() = _responseStory

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
            when(state) {
                is Resource.Success -> _responseMed.postValue(state.data)
                is Resource.Failure -> Log.d(TAG, "getAllMeditations Failure: ${state.error}")
            }
        }
    }

    private fun getAllStories() = viewModelScope.launch {
        storyRepository.getAllStories { state ->
            when(state) {
                is Resource.Success -> _responseStory.postValue(state.data)
                is Resource.Failure -> Log.d(TAG, "getAllStories Failure: ${state.error}")
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel: "
    }

}