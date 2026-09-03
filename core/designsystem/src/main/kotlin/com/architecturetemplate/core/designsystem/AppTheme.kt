package com.architecturetemplate.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme {

    private val lightModeColors = AppColor(
        background = Color(0xFFFEF7FF),
        onBackground = Color(0xFF1C1B1F),
        accent = Color(0xFF6750A4),
        onAccent = Color.White,
        cardContainer = Color(0xFFF3EDF7),
        onCardContainer = Color(0xFF49454F),
        textPrimary = Color(0xFF1C1B1F),
        textSecondary = Color(0xFF49454F),
        danger = Color(0xFFBA1A1A),
        onDanger = Color.White,
        divider = Color(0xFFC4C7C5)
    )

    private val darkModeColors = AppColor(
        background = Color(0xFF141218),
        onBackground = Color(0xFFE6E1E5),
        accent = Color(0xFFD0BCFF),
        onAccent = Color(0xFF381E72),
        cardContainer = Color(0xFF211F26),
        onCardContainer = Color(0xFFE6E1E5),
        textPrimary = Color(0xFFE6E1E5),
        textSecondary = Color(0xFFCAC4D0),
        danger = Color(0xFFFFB4AB),
        onDanger = Color(0xFF690005),
        divider = Color(0xFF49454F)
    )

    val colors: AppColor
        @ReadOnlyComposable
        @Composable
        get() = LocalAppColor.current

    val typography: AppTypography
        @ReadOnlyComposable
        @Composable
        get() = AppTypography(
            title = TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.SemiBold
            ),
            body = TextStyle(fontSize = 16.sp, lineHeight = 24.sp),
            label = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium),
        )

    val sizes: AppSizes
        @ReadOnlyComposable
        @Composable
        get() = AppSizes(
            s = 4.dp,
            m = 8.dp,
            l = 12.dp,
            xl = 16.dp,
            xxl = 24.dp
        )

    val fontSizes: AppFontSizes
        @ReadOnlyComposable
        @Composable
        get() = AppFontSizes(
            s = 4.sp,
            m = 8.sp,
            l = 12.sp,
            xl = 16.sp,
            xxl = 24.sp,
        )

    val shapes: AppShapes
        @ReadOnlyComposable
        @Composable
        get() = AppShapes(
            container = RoundedCornerShape(12.dp),
            pill = RoundedCornerShape(percent = 50),
        )

    @Composable
    operator fun invoke(
        isDarkMode: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (isDarkMode) darkModeColors else lightModeColors
        CompositionLocalProvider(
            LocalAppColor provides colors,
            LocalAppTypography provides typography,
            LocalAppSize provides sizes,
            LocalAppFontSize provides fontSizes,
            LocalAppShapes provides shapes,
            content = content
        )
    }
}

@Immutable
data class AppTypography(
    val title: TextStyle,
    val body: TextStyle,
    val label: TextStyle,
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        title = TextStyle.Default,
        body = TextStyle.Default,
        label = TextStyle.Default,
    )
}

@Immutable
data class AppColor(
    val background: Color,
    val onBackground: Color,
    val accent: Color,
    val onAccent: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val cardContainer: Color,
    val onCardContainer: Color,
    val danger: Color,
    val onDanger: Color,
    val divider: Color,
)

val LocalAppColor = staticCompositionLocalOf {
    AppColor(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        accent = Color.Unspecified,
        onAccent = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        cardContainer = Color.Unspecified,
        onCardContainer = Color.Unspecified,
        danger = Color.Unspecified,
        onDanger = Color.Unspecified,
        divider = Color.Unspecified,
    )
}

@Immutable
data class AppSizes(
    val s: Dp,
    val m: Dp,
    val l: Dp,
    val xl: Dp,
    val xxl: Dp,
)

val LocalAppSize = staticCompositionLocalOf {
    AppSizes(
        s = Dp.Unspecified,
        m = Dp.Unspecified,
        l = Dp.Unspecified,
        xl = Dp.Unspecified,
        xxl = Dp.Unspecified
    )
}

@Immutable
data class AppFontSizes(
    val s: TextUnit,
    val m: TextUnit,
    val l: TextUnit,
    val xl: TextUnit,
    val xxl: TextUnit,
)

val LocalAppFontSize = staticCompositionLocalOf {
    AppFontSizes(
        s = TextUnit.Unspecified,
        m = TextUnit.Unspecified,
        l = TextUnit.Unspecified,
        xl = TextUnit.Unspecified,
        xxl = TextUnit.Unspecified
    )
}

@Immutable
data class AppShapes(
    val container: CornerBasedShape,
    val pill: CornerBasedShape,
)

val LocalAppShapes = staticCompositionLocalOf {
    AppShapes(
        container = RoundedCornerShape(
            topStart = ZeroCornerSize,
            topEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize,
            bottomEnd = ZeroCornerSize
        ),
        pill = RoundedCornerShape(50)
    )
}