package com.postmate.presentation.user_profile

sealed class ProfileEvent {
    data class LoadUser(var userId: Int?, val name: String?, val email: String?) : ProfileEvent()

    data object SwipeToRefresh : ProfileEvent()
}
