package com.architecturetemplate.core.designsystem

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("RememberInComposition")
@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    this then Modifier.clickable(
        interactionSource = MutableInteractionSource(),
        indication = null,
        onClick = { onClick() }
    )