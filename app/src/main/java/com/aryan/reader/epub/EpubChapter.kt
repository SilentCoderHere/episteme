// EpubChapter.kt
package com.aryan.reader.epub

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class EpubChapter @OptIn(ExperimentalSerializationApi::class) constructor(
    @ProtoNumber(1) val chapterId: String,
    @ProtoNumber(2) val absPath: String,
    @ProtoNumber(3) val title: String,
    @ProtoNumber(4) val htmlFilePath: String,
    @ProtoNumber(5) val plainTextContent: String,
    @ProtoNumber(6) val htmlContent: String,
    @ProtoNumber(7) val depth: Int = 0,
    @ProtoNumber(8) val isInToc: Boolean = true
)