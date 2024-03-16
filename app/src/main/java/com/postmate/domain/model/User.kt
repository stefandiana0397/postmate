package com.postmate.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
) {
    val isActive: Boolean
        get() = status == IS_ACTIVE

    companion object {
        const val IS_ACTIVE: String = "active"

        val default =
            User(
                6777842,
                "Pres. Amogh Bhattathiri",
                "amogh_bhattathiri_pres@bahringer.test",
                "female",
                "active",
            )
    }
}
