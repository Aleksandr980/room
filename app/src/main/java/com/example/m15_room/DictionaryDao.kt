package com.example.m15_room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary ")
    fun getAll(): Flow<List<Dictionary>>

    @Insert(entity = Dictionary::class)
    suspend fun insert(dictionary: Dictionary)

    @Query("DELETE FROM dictionary")
    suspend fun delete()

    @Update
    suspend fun update(dictionary: Dictionary)

    @Query("SELECT * FROM dictionary WHERE word LIKE :word")
    fun contains(word: String): List<Dictionary>
}

