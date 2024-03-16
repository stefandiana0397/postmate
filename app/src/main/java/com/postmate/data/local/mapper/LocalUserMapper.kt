package com.postmate.data.local.mapper

import com.postmate.data.local.entity.UserEntity
import com.postmate.domain.model.User

// User
fun UserEntity.toUser() =
    User(
        id = id,
        name = name,
        email = email,
        gender = gender,
        status = status,
    )

fun User.toUserEntity() =
    UserEntity(
        id = id,
        name = name,
        email = email,
        gender = gender,
        status = status,
    )

fun List<UserEntity>.toUserList() = map { it.toUser() }

fun List<User>.toUserEntityList() = map { it.toUserEntity() }
