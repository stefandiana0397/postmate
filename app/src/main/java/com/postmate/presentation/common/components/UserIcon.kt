package com.postmate.presentation.common.components

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
import com.postmate.domain.model.User
import com.postmate.presentation.common.util.FormattingUtils
import com.postmate.presentation.common.util.FormattingUtils.displayPhoto

@Composable
fun UserIcon(user: User) {
    Box(
        modifier =
            Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center,
    ) {
        if (displayPhoto(user.id)) {
            SubcomposeAsyncImage(
                model = FormattingUtils.getPhotoUrl(user.name),
                contentDescription = "Profile image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
            )
        } else {
            Text(
                text = FormattingUtils.extractInitials(user.name),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}
