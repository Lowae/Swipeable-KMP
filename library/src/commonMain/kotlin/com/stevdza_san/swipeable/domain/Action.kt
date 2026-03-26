package com.stevdza_san.swipeable.domain

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable as ComposableAnnotation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource

/**
 * Describes the content rendered inside an action button.
 *
 * This enables action buttons to be rendered using:
 * - Drawable resources (Compose Resources)
 * - Image vectors (e.g. `Icons.Default.*`)
 * - Fully custom composable content
 */
sealed interface ActionContent {
    data class Resource(val icon: DrawableResource) : ActionContent
    data class Vector(val icon: ImageVector) : ActionContent
    data class Composable(val content: @ComposableAnnotation BoxScope.() -> Unit) : ActionContent
    data object None : ActionContent
}

/**
 * Comprehensive customization options for action button appearance.
 *
 * @param containerColor Background color of the button
 * @param icon The content to render inside the action button
 * @param iconSize Size of the icon within the button
 * @param padding Size of the button (determines touch target and visual size)
 * @param shape Shape of the button (CircleShape, RoundedCornerShape, etc.)
 * @param iconColor Tint color for built-in icon renderers. Use [Color.Unspecified] to disable tinting.
 */
data class ActionCustomization(
    val containerColor: Color,
    val icon: ActionContent = ActionContent.None,
    val iconSize: Dp = 24.dp,
    val iconColor: Color = Color.Unspecified,
    val shape: Shape = CircleShape,
    val padding: Dp = 48.dp,
) {
    companion object {
        operator fun invoke(
            icon: DrawableResource,
            containerColor: Color,
            iconSize: Dp = 24.dp,
            iconColor: Color = Color.Unspecified,
            shape: Shape = CircleShape,
            padding: Dp = 48.dp,
        ): ActionCustomization = ActionCustomization(
            containerColor = containerColor,
            icon = ActionContent.Resource(icon),
            iconSize = iconSize,
            iconColor = iconColor,
            shape = shape,
            padding = padding,
        )

        operator fun invoke(
            icon: ImageVector,
            containerColor: Color,
            iconSize: Dp = 24.dp,
            iconColor: Color = Color.Unspecified,
            shape: Shape = CircleShape,
            padding: Dp = 48.dp,
        ): ActionCustomization = ActionCustomization(
            containerColor = containerColor,
            icon = ActionContent.Vector(icon),
            iconSize = iconSize,
            iconColor = iconColor,
            shape = shape,
            padding = padding,
        )

        operator fun invoke(
            containerColor: Color,
            content: @ComposableAnnotation BoxScope.() -> Unit,
            iconSize: Dp = 24.dp,
            iconColor: Color = Color.Unspecified,
            shape: Shape = CircleShape,
            padding: Dp = 48.dp,
        ): ActionCustomization = ActionCustomization(
            containerColor = containerColor,
            icon = ActionContent.Composable(content),
            iconSize = iconSize,
            iconColor = iconColor,
            shape = shape,
            padding = padding,
        )
    }
}

/**
 * Represents a single swipe action with its behavior and customization.
 *
 * @param customization Visual appearance and styling options
 * @param onAction Callback triggered when the action is performed
 * @param label Optional accessibility label for the action
 */
data class SwipeAction(
    val customization: ActionCustomization,
    val onAction: () -> Unit,
    val label: String? = null
)
