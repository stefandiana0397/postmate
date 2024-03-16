package com.postmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.postmate.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<UserEntity>?

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(entries: List<UserEntity>)

    @Query(
        "DELETE FROM user WHERE id = :entryId",
    )
    suspend fun deleteUserById(entryId: Int)
}
