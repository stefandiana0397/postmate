package com.postmate.data.local.mapper

import com.postmate.data.local.entity.PostEntity
import com.postmate.domain.model.Post

// Post
fun PostEntity.toPost() =
    Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )

fun Post.toPostEntity() =
    PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )

fun List<PostEntity>.toPostList() = map { it.toPost() }

fun List<Post>.toPostEntityList() = map { it.toPostEntity() }
