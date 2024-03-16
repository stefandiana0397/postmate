package com.postmate.presentation.user_profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.postmate.R
import com.postmate.domain.model.Post
import com.postmate.domain.model.User
import com.postmate.presentation.common.AppToolbar
import com.postmate.presentation.common.AppToolbarText
import com.postmate.presentation.common.ToolbarAction
import com.postmate.presentation.common.UserIcon
import com.postmate.presentation.navigation.Screen
import com.postmate.presentation.ui.theme.spacingExtraLarge
import com.postmate.presentation.ui.theme.spacingLarge
import com.postmate.presentation.ui.theme.spacingMedium
import com.postmate.presentation.ui.theme.spacingSmall
import com.postmate.presentation.user_list.UserEvent
import com.postmate.presentation.user_list.UserListScreen
import com.postmate.presentation.user_list.UserState
import com.postmate.presentation.user_list.UserUtil
import com.postmate.presentation.user_list.components.UserItem
import com.postmate.presentation.user_profile.components.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileState: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    onScreenClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler(onBack = onScreenClose)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val refreshState = rememberSwipeRefreshState(isRefreshing = profileState.isLoading)
    Scaffold(
        topBar = {
            AppToolbar(
                title = {
                    AppToolbarText(
                        text = stringResource(id = R.string.detalii_contact),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                    )
                },
                navIcon = {
                    ToolbarAction(
                        image = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        onClick = onScreenClose,
                    )
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            UserIcon(displayPhoto = profileState.displayPhoto, name = profileState.selectedUser?.name ?: "")
            Spacer(modifier = Modifier.height(spacingSmall))
            Text(text = profileState.selectedUser?.name ?: "", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(spacingSmall))
            Text(text = profileState.selectedUser?.email ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(spacingMedium))
            SwipeRefresh(
                state = refreshState,
                onRefresh = { onEvent(ProfileEvent.SwipeToRefresh) },
            ) {
                if (profileState.posts.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(horizontal = spacingLarge, vertical = spacingLarge)
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                    ) {
                        Text(
                            textAlign = TextAlign.Start,
                            text = stringResource(id = R.string.no_data),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

                LazyColumn(
                    modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    contentPadding = PaddingValues(bottom = spacingExtraLarge),
                ) {
                    itemsIndexed(profileState.posts) { index, post ->
                        PostItem(
                            post = post,
                        )
                        if (index < profileState.posts.size - 1) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.02f),
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        profileState = ProfileState(posts = listOf(Post.default), selectedUser = User.default, displayPhoto = true),
        onEvent = {},
        onScreenClose = {},
    )
}