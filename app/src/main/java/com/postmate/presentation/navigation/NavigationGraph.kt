package com.postmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.postmate.presentation.user_list.UserListScreen
import com.postmate.presentation.user_list.UserListViewModel
import com.postmate.presentation.user_profile.ProfileEvent
import com.postmate.presentation.user_profile.ProfileScreen
import com.postmate.presentation.user_profile.ProfileViewModel

@Composable
fun NavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.UserListScreen.route) {
        composable(route = Screen.UserListScreen.route) {
            val viewModel = hiltViewModel<UserListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            UserListScreen(
                userState = state,
                onEvent = viewModel::onEvent,
                navigateTo = { navController.navigate(it) },
            )
        }
        composable(
            route =
                Screen.UserDetailsScreen.route +
                    "/{${ArgumentTypeEnum.ITEM_ID.name}}" +
                    "/{${ArgumentTypeEnum.NAME.name}}" +
                    "/{${ArgumentTypeEnum.EMAIL.name}}" +
                    "/{${ArgumentTypeEnum.DISPLAY_PHOTO.name}}",
            arguments =
                listOf(
                    navArgument(name = ArgumentTypeEnum.ITEM_ID.name) {
                        type = NavType.IntType
                        defaultValue = 0
                        nullable = false
                    },
                    navArgument(name = ArgumentTypeEnum.NAME.name) {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    },
                    navArgument(name = ArgumentTypeEnum.EMAIL.name) {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    },
                    navArgument(name = ArgumentTypeEnum.EMAIL.name) {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    },
                    navArgument(name = ArgumentTypeEnum.DISPLAY_PHOTO.name) {
                        type = NavType.BoolType
                        defaultValue = true
                        nullable = false
                    },
                ),
        ) { entry ->
            val viewModel = hiltViewModel<ProfileViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val userId = entry.arguments?.getInt(ArgumentTypeEnum.ITEM_ID.name, 0)
            val name = entry.arguments?.getString(ArgumentTypeEnum.NAME.name, "")
            val email = entry.arguments?.getString(ArgumentTypeEnum.EMAIL.name, "")
            val displayPhoto = entry.arguments?.getBoolean(ArgumentTypeEnum.DISPLAY_PHOTO.name, true)

            LaunchedEffect(key1 = userId) {
                viewModel.onEvent(ProfileEvent.LoadUser(userId, name, email, displayPhoto))
            }

            ProfileScreen(
                profileState = state,
                onEvent = viewModel::onEvent,
                onScreenClose = { navController.navigateUp() },
            )
        }
    }
}
