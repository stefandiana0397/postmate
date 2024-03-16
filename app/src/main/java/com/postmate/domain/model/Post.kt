package com.postmate.domain.model

class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
) {
    companion object {
        val default =
            Post(
                1,
                1,
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
            )
    }
}
