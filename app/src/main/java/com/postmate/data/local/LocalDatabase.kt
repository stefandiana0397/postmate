package com.postmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.postmate.data.local.dao.PostDao
import com.postmate.data.local.dao.UserDao
import com.postmate.data.local.entity.PostEntity
import com.postmate.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 1,
)
abstract class LocalDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val postDao: PostDao
}
