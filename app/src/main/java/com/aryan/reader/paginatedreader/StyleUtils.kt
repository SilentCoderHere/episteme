// StyleUtils.kt
package com.aryan.reader.paginatedreader

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp


fun parseCssDimensionToTextUnit(
    value: String,
    containerWidthPx: Int,
    density: Float
): TextUnit {
    if (density <= 0) return TextUnit.Unspecified
    val sanitizedValue = value.trim().lowercase()
    return when {
        sanitizedValue.endsWith("rem") -> sanitizedValue.removeSuffix("rem").toFloatOrNull()?.em ?: TextUnit.Unspecified
        sanitizedValue.endsWith("em") -> sanitizedValue.removeSuffix("em").toFloatOrNull()?.em ?: TextUnit.Unspecified
        sanitizedValue.endsWith("px") -> {
            val px = sanitizedValue.removeSuffix("px").toFloatOrNull() ?: 0f
            (px / density).sp
        }
        sanitizedValue.endsWith("pt") -> {
            val pt = sanitizedValue.removeSuffix("pt").toFloatOrNull() ?: 0f
            val px = pt * (4f / 3f)
            (px / density).sp
        }
        sanitizedValue.endsWith("%") -> {
            val percentage = sanitizedValue.removeSuffix("%").toFloatOrNull() ?: 0f
            if (containerWidthPx > 0) {
                val px = (percentage / 100f) * containerWidthPx
                (px / density).sp
            } else {
                TextUnit.Unspecified
            }
        }
        else -> TextUnit.Unspecified
    }
}

fun parseCssSizeToDp(
    value: String,
    baseFontSizeSp: Float,
    density: Float,
    containerWidthPx: Int
): Dp {
    if (density <= 0) return 0.dp
    val sanitizedValue = value.trim().lowercase()

    return when {
        sanitizedValue.endsWith("px") -> {
            val px = sanitizedValue.removeSuffix("px").toFloatOrNull() ?: 0f
            (px / density).dp
        }
        sanitizedValue.endsWith("rem") -> {
            val rem = sanitizedValue.removeSuffix("rem").toFloatOrNull() ?: 0f
            (rem * baseFontSizeSp).dp
        }
        sanitizedValue.endsWith("em") -> {
            val em = sanitizedValue.removeSuffix("em").toFloatOrNull() ?: 0f
            (em * baseFontSizeSp).dp
        }
        sanitizedValue.endsWith("pt") -> {
            val pt = sanitizedValue.removeSuffix("pt").toFloatOrNull() ?: 0f
            val px = pt * (4f / 3f)
            (px / density).dp
        }
        sanitizedValue.endsWith("%") -> {
            val percentage = sanitizedValue.removeSuffix("%").toFloatOrNull() ?: 0f
            if (containerWidthPx > 0) {
                val px = (percentage / 100f) * containerWidthPx
                (px / density).dp
            } else {
                0.dp
            }
        }
        else -> 0.dp
    }
}