package com.postmate.data.repository

import com.postmate.data.local.dao.PostDao
import com.postmate.data.local.mapper.toPostEntityList
import com.postmate.data.local.mapper.toPostList
import com.postmate.data.remote.GorestAPI
import com.postmate.data.remote.mapper.toPostList
import com.postmate.domain.model.Post
import com.postmate.domain.model.User
import com.postmate.domain.repository.IPostRepository
import com.postmate.presentation.common.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostRepositoryImpl
    @Inject
    constructor(private val api: GorestAPI, private val postDao: PostDao) : IPostRepository {
        override suspend fun fetchPostsByUser(user: User) =
            flow<Resource<List<Post>>> {
                var localPosts = postDao.getPostsByUserId(user.id)?.toPostList()?.sortedBy { it.title } ?: emptyList()
                emit(Resource.Loading(localPosts))

                try {
                    val response = api.getPostsByUser(user.id)
                    val remoteUsers = response.body()?.toPostList()
                    val postEntities = remoteUsers?.toPostEntityList()
                    postDao.deletePostById(user.id)
                    postEntities?.let { postDao.insertPosts(it) }
                    localPosts = postEntities?.toPostList()?.sortedBy { it.title } ?: localPosts
                    emit(Resource.Success(localPosts))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = e.message ?: "Invalid Response", data = localPosts))
                } catch (e: IOException) {
                    emit(Resource.Error(message = e.message ?: "Couldn't reach server", data = localPosts))
                }
            }.flowOn(Dispatchers.IO)
    }
