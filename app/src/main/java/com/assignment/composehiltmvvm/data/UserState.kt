package com.assignment.composehiltmvvm.data

import com.assignment.composehiltmvvm.model.UserResponse

sealed class UserState {
    object Loading : UserState()
    data class OnUserLoad(val userResponse: UserResponse) : UserState()
    data class OnError(val message: String) : UserState()
}