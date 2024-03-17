package com.postmate.domain.use_cases

import com.postmate.domain.model.User
import com.postmate.domain.repository.IPostRepository
import com.postmate.presentation.common.util.Resource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchPostByUserUseCase
    @Inject
    constructor(
        private val postRepository: IPostRepository,
    ) {
        suspend fun execute(user: User) =
            postRepository.fetchPostsByUser(user).map { resource ->
                val posts = resource.data?.firstOrNull()?.let { listOf(it) } ?: emptyList()
                when (resource) {
                    is Resource.Loading -> Resource.Loading(posts)
                    is Resource.Success -> Resource.Success(posts)
                    is Resource.Error -> Resource.Error(message = resource.message ?: "", data = posts)
                }
            }
    }
