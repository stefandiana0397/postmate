package com.postmate.presentation.navigation

import com.postmate.util.Constants.ROUTE_USER_DETAILS
import com.postmate.util.Constants.ROUTE_USER_LIST

sealed class Screen(val route: String) {
    data object UserListScreen : Screen(ROUTE_USER_LIST)

    data object UserDetailsScreen : Screen(ROUTE_USER_DETAILS)
}
