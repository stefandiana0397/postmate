package com.postmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
)
