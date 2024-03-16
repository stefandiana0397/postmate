package com.postmate.data.remote

import com.postmate.data.remote.dto.PostDto
import com.postmate.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GorestAPI {
    @GET("/public/v2/users/")
    suspend fun getUsers(): Response<List<UserDto>>

    @GET("public/v2/users/{userId}/posts")
    suspend fun getPostsByUser(
        @Path("userId") userId: Int,
    ): Response<List<PostDto>>
}
