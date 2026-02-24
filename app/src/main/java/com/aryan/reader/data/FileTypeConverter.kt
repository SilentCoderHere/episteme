// FileTypeConverter.kt
package com.aryan.reader.data

import androidx.room.TypeConverter
import com.aryan.reader.FileType

class FileTypeConverter {
    @TypeConverter
    fun fromFileType(fileType: FileType?): String? {
        return fileType?.name
    }

    @TypeConverter
    fun toFileType(name: String?): FileType? {
        return name?.let { FileType.valueOf(it) }
    }
}