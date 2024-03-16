package com.postmate.presentation.user_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.postmate.domain.model.User
import com.postmate.presentation.common.UserIcon
import com.postmate.presentation.ui.theme.spacingMedium

@Composable
fun UserItem(
    user: User,
    displayPhoto: Boolean,
    onClick: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(
                text = user.name + " " + user.id,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trailingContent = {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Localized description",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            )
        },
        leadingContent = {
            UserIcon(displayPhoto = displayPhoto, name = user.name)
        },
        modifier =
            modifier
                .semantics(mergeDescendants = true) {
                    selected = true
                }
                .clickable(enabled = true, onClick = { onClick(user) })
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = spacingMedium),
    )
}

@Preview
@Composable
fun UserItemPreview() {
    UserItem(
        user = User.default,
        displayPhoto = false,
        onClick = {},
    )
}
