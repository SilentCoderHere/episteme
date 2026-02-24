package com.aryan.reader.paginatedreader

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.isSpecified
import com.aryan.reader.epub.EpubChapter
import kotlin.math.ceil
import kotlin.math.max

object PageCountEstimator {

    /**
     * heuristic factor: approximate percentage of HTML string that is actual text vs tags.
     * 0.6 means we assume 60% of the string length is visible text.
     */
    private const val HTML_TEXT_DENSITY_FACTOR = 0.6f

    /**
     * Calculates an approximate page count instantly without rendering.
     */
    fun estimateChapterPageCount(
        chapter: EpubChapter,
        constraints: Constraints,
        textStyle: TextStyle,
        density: Density
    ): Int {
        val screenWidth = constraints.maxWidth
        val screenHeight = constraints.maxHeight
        val screenArea = screenWidth * screenHeight

        if (screenArea <= 0) return 1

        val fontSizePx = with(density) { textStyle.fontSize.toPx() }

        val lineHeightPx = if (textStyle.lineHeight.isSpecified) {
            with(density) { textStyle.lineHeight.toPx() }
        } else {
            fontSizePx * 1.4f
        }

        val avgCharWidthPx = fontSizePx * 0.6f

        val charArea = avgCharWidthPx * lineHeightPx

        val rawCharsPerPage = screenArea / charArea

        val packingFactor = 0.75f
        val estimatedVisibleCharsPerPage = (rawCharsPerPage * packingFactor).toInt()

        if (estimatedVisibleCharsPerPage <= 0) return 1

        val estimatedTextLength = (chapter.htmlContent.length * HTML_TEXT_DENSITY_FACTOR).toInt()

        val pages = ceil(estimatedTextLength.toFloat() / estimatedVisibleCharsPerPage).toInt()

        return max(1, pages)
    }
}