package com.aryan.reader.pdf.ocr

import android.graphics.Rect

/**
 * Platform-agnostic OCR result models to decouple the app from Google ML Kit.
 */
data class OcrResult(
    val text: String,
    val textBlocks: List<OcrBlock>
)

data class OcrBlock(
    val text: String,
    val boundingBox: Rect?,
    val lines: List<OcrLine>
)

data class OcrLine(
    val text: String,
    val boundingBox: Rect?,
    val elements: List<OcrElement>
)

data class OcrElement(
    val text: String,
    val boundingBox: Rect?,
    val symbols: List<OcrSymbol>
)

data class OcrSymbol(
    val text: String,
    val boundingBox: Rect?
)