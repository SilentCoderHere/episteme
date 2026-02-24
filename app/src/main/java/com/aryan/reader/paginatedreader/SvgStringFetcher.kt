// SvgStringFetcher.kt
package com.aryan.reader.paginatedreader

import timber.log.Timber
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import okio.Buffer

/**
 * A custom data class to wrap raw SVG string content.
 * This avoids conflicts with Coil's default String fetcher.
 */
data class SvgData(val content: String)

/**
 * A custom Coil Fetcher that handles loading SVG data from our [SvgData] class.
 */
class SvgStringFetcher(
    private val options: Options,
    private val data: SvgData,
) : Fetcher {

    override suspend fun fetch(): FetchResult {
        Timber.d("SvgStringFetcher: fetching SVG data from SvgData object.")
        val buffer = Buffer().writeUtf8(data.content)
        return SourceResult(
            source = ImageSource(buffer, options.context),
            mimeType = "image/svg+xml",
            dataSource = DataSource.MEMORY
        )
    }

    class Factory : Fetcher.Factory<SvgData> {
        override fun create(data: SvgData, options: Options, imageLoader: coil.ImageLoader): Fetcher {
            Timber.d("SvgStringFetcher.Factory: create called for SvgData.")
            return SvgStringFetcher(options, data)
        }
    }
}