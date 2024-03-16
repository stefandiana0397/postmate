package com.postmate.data.remote.mapper

import com.postmate.data.remote.dto.PostDto
import com.postmate.domain.model.Post

fun PostDto.toPost() =
    Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )

fun Post.toPostDto() =
    PostDto(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )

fun List<PostDto>.toPostList() = map { it.toPost() }

fun List<Post>.toPostDtoList() = map { it.toPostDto() }
