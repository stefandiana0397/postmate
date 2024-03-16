package com.postmate.domain.use_cases

import com.postmate.domain.repository.IUserRepository
import com.postmate.presentation.common.util.Resource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchActiveUsersUseCase
    @Inject
    constructor(
        private val userRepository: IUserRepository,
    ) {
        suspend fun execute() =
            userRepository.fetchUsers().map { resource ->
                val activeUsers = resource.data?.filter { it.isActive }?.sortedBy { it.name } ?: emptyList()
                when (resource) {
                    is Resource.Loading -> Resource.Loading(activeUsers)
                    is Resource.Success -> Resource.Success(activeUsers)
                    is Resource.Error -> Resource.Error(message = resource.message ?: "", data = activeUsers)
                }
            }
    }
