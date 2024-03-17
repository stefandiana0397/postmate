package com.postmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post")
data class PostEntity(
    @SerializedName("local_id")
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val id: Int,
    val body: String,
    val title: String,
    @SerializedName("user_id")
    val userId: Int,
)
