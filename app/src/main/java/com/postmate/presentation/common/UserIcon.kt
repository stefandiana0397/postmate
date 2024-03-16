package com.postmate.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.postmate.presentation.user_list.UserUtil

@Composable
fun UserIcon(
    displayPhoto: Boolean,
    name: String,
) {
    Box(
        modifier =
            Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center,
    ) {
        if (displayPhoto) {
            SubcomposeAsyncImage(
                model = UserUtil.getPhotoUrl(name),
                contentDescription = "Profile image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
            )
        } else {
            Text(
                text = UserUtil.extractInitials(name),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}
