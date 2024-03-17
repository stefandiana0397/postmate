package com.postmate.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.postmate.R
import com.postmate.presentation.ui.theme.spacingLarge

@Composable
fun NoContentItem(
    noContent: Boolean,
    colorText: Color,
    modifier: Modifier = Modifier,
) {
    if (noContent) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(spacingLarge),
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.no_data),
                color = colorText,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
