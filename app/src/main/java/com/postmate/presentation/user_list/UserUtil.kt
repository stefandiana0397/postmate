package com.postmate.presentation.user_list

object UserUtil {
    fun extractInitials(fullName: String): String {
        val words = fullName.split(" ")
        val initials = words.mapNotNull { it.firstOrNull()?.uppercaseChar() }
        return initials.joinToString(" ")
    }

    fun displayPhoto(id: Int): Boolean = id % 2 != 0

    fun getPhotoUrl(name: String): String = "https://picsum.photos/seed/$name/200/200"
}
