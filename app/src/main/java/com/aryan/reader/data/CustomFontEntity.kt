package com.aryan.reader.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_fonts")
data class CustomFontEntity(
    @PrimaryKey val id: String, // UUID
    val displayName: String,
    val fileName: String, // The actual filename on disk (e.g., font_uuid.ttf)
    val fileExtension: String, // ttf, otf, woff2
    val path: String, // Absolute path to the file
    val timestamp: Long,
    @ColumnInfo(defaultValue = "0") val isDeleted: Boolean = false
)