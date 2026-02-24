// MyApplication.kt
package com.aryan.reader

import android.app.Application
import android.webkit.WebView
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import com.aryan.reader.paginatedreader.SvgStringFetcher
import timber.log.Timber // Add this

class MyApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    override fun newImageLoader(): ImageLoader {
        Timber.d("MyApplication: Creating custom ImageLoader with SvgStringFetcher.")
        return ImageLoader.Builder(this)
            .components {
                add(SvgStringFetcher.Factory())
                add(SvgDecoder.Factory())
            }
            .build()
    }
}