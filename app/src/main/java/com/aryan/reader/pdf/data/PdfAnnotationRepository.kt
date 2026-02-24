// PdfAnnotationRepository.kt
package com.aryan.reader.pdf.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

class PdfAnnotationRepository(private val context: Context) {

    private fun getFile(bookId: String): File {
        val safeBookId = bookId.replace("/", "_")
        val dir = File(context.filesDir, "annotations")
        if (!dir.exists()) dir.mkdirs()
        return File(dir, "annotation_$safeBookId.json")
    }

    suspend fun saveAnnotations(bookId: String, annotations: Map<Int, List<PdfAnnotation>>) {
        withContext(Dispatchers.IO) {
            try {
                Timber.tag("AnnotationSync").d("Start saving local JSON for $bookId. Count: ${annotations.size}")

                if (annotations.isEmpty()) {
                    return@withContext
                }

                val json = AnnotationSerializer.toJson(annotations)
                val file = getFile(bookId)
                file.writeText(json)

                Timber.tag("AnnotationSync").d("Finished saving local JSON for $bookId. Path: ${file.absolutePath}, Size: ${file.length()}")
            } catch (e: Exception) {
                Timber.tag("AnnotationSync").e(e, "Failed to save local annotations")
            }
        }
    }

    suspend fun loadAnnotations(bookId: String): Map<Int, List<PdfAnnotation>> {
        return withContext(Dispatchers.IO) {
            try {
                val file = getFile(bookId)
                if (file.exists()) {
                    val json = file.readText()
                    Timber.tag("AnnotationSync").d("Loaded local JSON for $bookId. Size: ${file.length()}")
                    AnnotationSerializer.fromJson(json)
                } else {
                    Timber.tag("AnnotationSync").d("No local annotation file found for $bookId")
                    emptyMap()
                }
            } catch (e: Exception) {
                Timber.tag("AnnotationSync").e(e, "Failed to load local annotations")
                emptyMap()
            }
        }
    }

    fun getAnnotationFileForSync(bookId: String): File? {
        val file = getFile(bookId)
        val valid = file.exists() && file.length() > 0

        Timber.tag("AnnotationSync").d("Checking file for sync: $bookId. Exists: ${file.exists()}, Size: ${file.length()} bytes. Valid: $valid")

        return if (valid) file else null
    }
}