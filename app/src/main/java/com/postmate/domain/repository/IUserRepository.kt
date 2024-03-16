package com.postmate.domain.repository

import com.postmate.domain.model.User
import com.postmate.presentation.common.util.Resource
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun fetchUsers(): Flow<Resource<List<User>>>
}
