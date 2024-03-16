package com.postmate.domain.use_cases

import com.postmate.domain.model.User
import com.postmate.domain.repository.IPostRepository
import com.postmate.presentation.common.util.Resource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchPostsByUserUseCase
    @Inject
    constructor(
        private val postRepository: IPostRepository,
    ) {
        suspend fun execute(user: User) =
            postRepository.fetchPostsByUser(user).map { resource ->
                when (resource) {
                    is Resource.Loading, is Resource.Success -> {
                        Resource.Success(resource.data ?: emptyList())
                    }
                    else -> resource
                }
            }
    }
