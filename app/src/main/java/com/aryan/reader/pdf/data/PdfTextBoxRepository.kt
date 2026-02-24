package com.aryan.reader.pdf.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class PdfTextBoxRepository(private val context: Context) {

    private fun getFile(bookId: String): File {
        val safeBookId = bookId.replace("/", "_")
        val dir = File(context.filesDir, "textboxes")
        if (!dir.exists()) dir.mkdirs()
        return File(dir, "textboxes_$safeBookId.json")
    }

    suspend fun saveTextBoxes(bookId: String, textBoxes: List<PdfTextBox>) {
        withContext(Dispatchers.IO) {
            if (textBoxes.isEmpty()) {
                val file = getFile(bookId)
                if (file.exists()) file.delete()
                return@withContext
            }
            val json = TextBoxSerializer.toJson(textBoxes)
            getFile(bookId).writeText(json)
        }
    }

    suspend fun loadTextBoxes(bookId: String): List<PdfTextBox> {
        return withContext(Dispatchers.IO) {
            val file = getFile(bookId)
            if (file.exists()) {
                TextBoxSerializer.fromJson(file.readText())
            } else {
                emptyList()
            }
        }
    }

    fun getFileForSync(bookId: String): File {
        return getFile(bookId)
    }

    fun clearAll() {
        val dir = File(context.filesDir, "textboxes")
        if (dir.exists()) {
            dir.listFiles()?.forEach { it.delete() }
        }
    }

    fun deleteForBook(bookId: String) {
        val file = getFile(bookId)
        if(file.exists()) file.delete()
    }
}