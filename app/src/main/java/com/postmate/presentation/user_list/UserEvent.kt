package com.postmate.presentation.user_list

import com.postmate.domain.model.User

sealed class UserEvent {
    data class SelectUser(val data: User) : UserEvent()

    data object SwipeToRefresh : UserEvent()
}
