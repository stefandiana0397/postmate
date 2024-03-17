package com.postmate.presentation.user_profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.postmate.R
import com.postmate.domain.model.Post
import com.postmate.domain.model.User
import com.postmate.presentation.common.components.AppToolbar
import com.postmate.presentation.common.components.AppToolbarText
import com.postmate.presentation.common.components.ErrorItem
import com.postmate.presentation.common.components.NoContentItem
import com.postmate.presentation.common.components.ToolbarAction
import com.postmate.presentation.common.components.UserIcon
import com.postmate.presentation.ui.theme.spacingExtraLarge
import com.postmate.presentation.ui.theme.spacingMedium
import com.postmate.presentation.ui.theme.spacingSmall
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

    Scaffold(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            AppToolbar(
                title = {
                    AppToolbarText(
                        text = stringResource(id = R.string.detalii_contact),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
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
            modifier = modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            profileState.selectedUser?.let {
                TopSection(user = it)
                Spacer(modifier = Modifier.height(spacingMedium))
                PostSection(posts = profileState.posts, isLoading = profileState.isLoading, error = profileState.error, onEvent = onEvent)
            } ?: run {
                NoContentItem(
                    noContent = true,
                    colorText = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer),
                )
            }
        }
    }
}

@Composable
fun TopSection(
    user: User,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        UserIcon(user = user)
        Spacer(modifier = Modifier.height(spacingSmall))
        Text(
            text = user.name,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(spacingSmall))
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostSection(
    posts: List<Post>,
    isLoading: Boolean,
    error: String?,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    SwipeRefresh(
        state = refreshState,
        onRefresh = { onEvent(ProfileEvent.SwipeToRefresh) },
    ) {
        LazyColumn(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(bottom = spacingExtraLarge),
        ) {
            item {
                NoContentItem(
                    noContent = posts.isEmpty(),
                    colorText = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer),
                )
            }
            item {
                ErrorItem(error = error)
            }
            itemsIndexed(posts) { index, post ->
                PostItem(post = post)
                if (index < posts.size - 1) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.02f))
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        profileState = ProfileState(posts = listOf(Post.default), selectedUser = User.default),
        onEvent = {},
        onScreenClose = {},
    )
}
