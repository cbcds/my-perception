package com.cbcds.myperception.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class UserViewModel : ViewModel() {
    enum class AuthState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authState = UserLiveData().map { user ->
        if (user != null) AuthState.AUTHENTICATED else AuthState.UNAUTHENTICATED
    }
}