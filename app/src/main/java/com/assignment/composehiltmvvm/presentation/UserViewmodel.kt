package com.assignment.composehiltmvvm.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.composehiltmvvm.data.UserRepository
import com.assignment.composehiltmvvm.data.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)

    val userState: StateFlow<UserState>
        get() = _userState

    lateinit var job: Job

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable.message.toString())
    }

    private fun onError(message: String) {
        _userState.value = UserState.OnError(message)
    }

    fun getUser() {
        job = viewModelScope.launch(coroutineExceptionHandler) {
            userRepository.getUser().collect {
                _userState.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}