package com.example.dictionaryapplicationkotlin.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dictionaryapplicationkotlin.model.dbClass

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(word:dbClass)

    @Query("SELECT * FROM word_room_db ORDER BY id DESC")
    fun readAllWords():LiveData<List<dbClass>>

    @Query("DELETE FROM word_room_db")
    suspend fun deleteAllWords()

    @Delete
    suspend fun deleteWord(word:dbClass)



}