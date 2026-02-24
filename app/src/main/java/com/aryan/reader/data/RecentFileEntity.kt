// RecentFileEntity.kt
package com.aryan.reader.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aryan.reader.FileType

@Entity(tableName = "recent_files")
@TypeConverters(FileTypeConverter::class)
data class RecentFileEntity(
    @PrimaryKey val bookId: String,
    val uriString: String?,
    val type: FileType,
    val displayName: String,
    val timestamp: Long,
    val coverImagePath: String?,
    val title: String?,
    val author: String?,
    @ColumnInfo(name = "lastChapterIndex") val lastChapterIndex: Int?,
    val lastPage: Int?,
    @ColumnInfo(name = "lastPositionCfi") val lastPositionCfi: String?,
    @ColumnInfo(name = "progressPercentage") val progressPercentage: Float?,
    @ColumnInfo(defaultValue = "1") val isRecent: Boolean,
    @ColumnInfo(defaultValue = "1") val isAvailable: Boolean,
    val lastModifiedTimestamp: Long,
    @ColumnInfo(defaultValue = "0") val isDeleted: Boolean,
    val locatorBlockIndex: Int?,
    val locatorCharOffset: Int?,
    val bookmarks: String?,
    @ColumnInfo(defaultValue = "NULL") val sourceFolderUri: String?
)