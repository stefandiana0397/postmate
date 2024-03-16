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
                when (resource) {
                    is Resource.Loading, is Resource.Success -> {
                        val activeUsers = resource.data?.filter { it.isActive }
                        Resource.Success(activeUsers ?: emptyList())
                    }
                    else -> resource
                }
            }
    }
