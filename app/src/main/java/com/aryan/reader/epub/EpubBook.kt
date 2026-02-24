package com.aryan.reader.epub

import android.graphics.Bitmap
import com.aryan.reader.epub.EpubParser.EpubPageTarget
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class EpubTocEntry(
    val label: String,
    val absolutePath: String,
    val fragmentId: String?,
    val depth: Int
)

@Serializable
data class EpubBook(
    val fileName: String,
    val title: String,
    val author: String,
    val language: String,
    @Serializable(with = BitmapSerializer::class) val coverImage: Bitmap?,
    val chapters: List<EpubChapter> = emptyList(),
    val images: List<EpubImage> = emptyList(),
    val pageList: List<EpubPageTarget> = emptyList(),
    val tableOfContents: List<EpubTocEntry> = emptyList(),
    val extractionBasePath: String = "",
    val css: Map<String, String> = emptyMap(),
    @Transient
    val chaptersForPagination: List<EpubChapter> = chapters
)