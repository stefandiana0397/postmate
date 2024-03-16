package com.postmate.presentation.user_list

import com.postmate.domain.model.User

data class UserState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedUser: User? = null,
)
