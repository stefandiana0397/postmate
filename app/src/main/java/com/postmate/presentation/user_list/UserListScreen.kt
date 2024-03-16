package com.postmate.presentation.user_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
// import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.postmate.R
import com.postmate.domain.model.User
import com.postmate.presentation.common.components.AppToolbar
import com.postmate.presentation.common.components.AppToolbarText
import com.postmate.presentation.navigation.Screen
import com.postmate.presentation.ui.theme.spacingExtraLarge
import com.postmate.presentation.ui.theme.spacingMedium
import com.postmate.presentation.ui.theme.spacingSmall
import com.postmate.presentation.common.util.FormattingUtils.displayPhoto
import com.postmate.presentation.user_list.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    userState: UserState,
    onEvent: (UserEvent) -> Unit,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val refreshState = rememberSwipeRefreshState(isRefreshing = userState.isLoading)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppToolbar(
                title = {
                    AppToolbarText(
                        text = stringResource(id = R.string.contacte),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                scrollBehavior = scrollBehavior,
                extended = true,
            )
        },
        contentWindowInsets = WindowInsets(top = 0.dp),
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = R.string.contactele_mele).uppercase(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth().padding(start = spacingMedium, top = spacingSmall, bottom = spacingSmall),
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f),
            )
            SwipeRefresh(
                state = refreshState,
                onRefresh = { onEvent(UserEvent.SwipeToRefresh) },
            ) {
                LazyColumn(
                    modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    contentPadding = PaddingValues(bottom = spacingExtraLarge),
                ) {
                    item {
                        if (userState.users.isEmpty()) {
                            Box(
                                modifier =
                                    Modifier.fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(spacingMedium),
                            ) {
                                Text(
                                    textAlign = TextAlign.Start,
                                    text = stringResource(id = R.string.no_data),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                    itemsIndexed(userState.users) { index, user ->
                        UserItem(
                            user = user,
                            displayPhoto = displayPhoto(user.id),
                            onClick = {
                                onEvent(UserEvent.SelectUser(user))
                                navigateTo(Screen.UserDetailsScreen.createRoute(user))
                            },
                        )
                        if (index < userState.users.size - 1) {
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

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    UserListScreen(
        userState = UserState(users = listOf(User.default)),
        onEvent = {},
        navigateTo = {},
    )
}

@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun UserListScreenPreview2() {
    UserListScreen(
        userState = UserState(users = listOf()),
        onEvent = {},
        navigateTo = {},
    )
}
