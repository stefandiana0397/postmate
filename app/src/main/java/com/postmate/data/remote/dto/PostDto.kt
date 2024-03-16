package com.postmate.data.remote.dto

import com.squareup.moshi.Json

data class PostDto(
    val id: Int,
    @field:Json(name = "user_id") val userId: Int,
    val title: String,
    val body: String,
)
