package com.postmate.domain.repository

import com.postmate.domain.model.Post
import com.postmate.domain.model.User
import com.postmate.presentation.common.util.Resource
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    suspend fun fetchPostsByUser(user: User): Flow<Resource<List<Post>>>
}
