package com.postmate.data.repository

import com.postmate.data.local.dao.UserDao
import com.postmate.data.local.mapper.toUserEntityList
import com.postmate.data.local.mapper.toUserList
import com.postmate.data.remote.GorestAPI
import com.postmate.data.remote.mapper.toUserList
import com.postmate.domain.model.User
import com.postmate.domain.repository.IUserRepository
import com.postmate.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(private val api: GorestAPI, private val userDao: UserDao) : IUserRepository {
        override suspend fun fetchUsers() =
            flow<Resource<List<User>>> {
                delay(1000L)
                var localUsers = userDao.getUsers()?.toUserList()?.sortedBy { it.name } ?: emptyList()
                emit(Resource.Loading(localUsers))

                try {
                    val response = api.getUsers()
                    val remoteUsers = response.body()?.toUserList()
                    val userEntities = remoteUsers?.toUserEntityList()
                    userDao.deleteAll()
                    userEntities?.let { userDao.insertUsers(it) }
                    localUsers = userEntities?.toUserList()?.sortedBy { it.name } ?: localUsers
                    emit(Resource.Success(localUsers))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = e.message ?: "Invalid Response", data = localUsers))
                } catch (e: IOException) {
                    emit(Resource.Error(message = e.message ?: "Couldn't reach server", data = localUsers))
                }
            }.flowOn(Dispatchers.IO)
    }
