// CustomFontDao.kt
package com.aryan.reader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFontDao {
    @Query("SELECT * FROM custom_fonts WHERE isDeleted = 0 ORDER BY displayName ASC")
    fun getAllFonts(): Flow<List<CustomFontEntity>>

    @Query("SELECT * FROM custom_fonts WHERE isDeleted = 0")
    suspend fun getAllFontsList(): List<CustomFontEntity>

    @Query("SELECT * FROM custom_fonts")
    suspend fun getAllFontsIncludingDeleted(): List<CustomFontEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFont(font: CustomFontEntity)

    @Query("SELECT * FROM custom_fonts WHERE id = :id")
    suspend fun getFontById(id: String): CustomFontEntity?

    @Query("UPDATE custom_fonts SET isDeleted = 1 WHERE id = :id")
    suspend fun markAsDeleted(id: String)

    @Query("DELETE FROM custom_fonts WHERE id = :id")
    suspend fun deletePermanently(id: String)

    @Query("SELECT * FROM custom_fonts WHERE fileName = :fileName LIMIT 1")
    suspend fun getFontByFileName(fileName: String): CustomFontEntity?
}