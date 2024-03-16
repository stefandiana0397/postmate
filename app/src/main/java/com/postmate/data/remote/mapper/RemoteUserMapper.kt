package com.postmate.data.remote.mapper

import com.postmate.data.remote.dto.UserDto
import com.postmate.domain.model.User

fun UserDto.toUser() =
    User(
        id = id,
        name = name,
        email = email,
        gender = gender,
        status = status,
    )

fun User.toUserDto() =
    UserDto(
        id = id,
        name = name,
        email = email,
        gender = gender,
        status = status,
    )

fun List<UserDto>.toUserList() = map { it.toUser() }

fun List<User>.toUserDtoList() = map { it.toUserDto() }
