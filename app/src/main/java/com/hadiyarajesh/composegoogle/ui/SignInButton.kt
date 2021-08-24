package com.hadiyarajesh.composegoogle.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadiyarajesh.composegoogle.R
import com.hadiyarajesh.composegoogle.ui.theme.Shapes


@ExperimentalMaterialApi
@Composable
fun SignInButton(
    text: String,
    loadingText: String = "Signing in...",
    icon: Painter,
    isLoading: Boolean = false,
    shape: Shape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        ),
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = if (isLoading) loadingText else text)
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun SignInButtonPreview() {
    SignInButton(
        text = "Sign in with Google",
        loadingText = "Signing in...",
        isLoading = false,
        icon = painterResource(id = R.drawable.ic_google_logo),
        onClick = { }
    )
}