package com.cbcds.myperception.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

open class UserViewModel : ViewModel() {
    enum class AuthState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authState = UserLiveData().map { user ->
        if (user != null) AuthState.AUTHENTICATED else AuthState.UNAUTHENTICATED
    }
}