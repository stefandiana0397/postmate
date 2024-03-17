package com.postmate.presentation.user_profile

import com.postmate.domain.model.Post
import com.postmate.domain.model.User

data class ProfileState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedUser: User? = null,
)
