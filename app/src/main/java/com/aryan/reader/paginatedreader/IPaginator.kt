// IPaginator.kt
package com.aryan.reader.paginatedreader

import androidx.compose.runtime.Stable
import com.aryan.reader.SearchResult
import kotlinx.coroutines.flow.Flow

@Stable
interface IPaginator {
    val totalPageCount: Int
    val isLoading: Boolean
    val generation: Int
    val pageShiftRequest: Flow<Int>

    fun getPageContent(pageIndex: Int): Page?
    fun getChapterPathForPage(pageIndex: Int): String?
    fun getPlainTextForChapter(chapterIndex: Int): String?
    fun navigateToHref(
        currentChapterAbsPath: String,
        href: String,
        onNavigationComplete: (pageIndex: Int) -> Unit
    )
    fun findPageForSearchResult(
        result: SearchResult,
        onResult: (pageIndex: Int) -> Unit
    )
    fun findPageForAnchor(
        chapterIndex: Int,
        anchor: String?,
        onResult: (pageIndex: Int) -> Unit
    )
    fun findPageForCfi(chapterIndex: Int, cfi: String, onResult: (pageIndex: Int) -> Unit)
    fun findPageForCfiAndOffset(chapterIndex: Int, cfi: String, charOffset: Int): Int?
    fun findChapterIndexForPage(pageIndex: Int): Int?
    fun getCfiForPage(pageIndex: Int): String?
    fun onUserScrolledTo(pageIndex: Int)
    fun getActiveAnchorForPage(pageIndex: Int, tocAnchors: List<String>): String?
}