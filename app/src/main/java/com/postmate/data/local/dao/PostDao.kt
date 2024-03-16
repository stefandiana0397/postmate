package com.postmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postmate.data.local.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getPosts(): List<PostEntity>?

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(entries: List<PostEntity>)

    @Query("DELETE FROM post WHERE userId = :userId")
    suspend fun deletePostsByUserId(userId: Int)

    @Query("SELECT * FROM post WHERE userId = :userId")
    suspend fun getPostsByUserId(userId: Int): List<PostEntity>?
}
