package com.example.dictionaryapplicationkotlin.repository

import androidx.lifecycle.LiveData
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.service.DictionaryDao

class wordRepository(val wordDao:DictionaryDao) {
    val readAllWords:LiveData<List<dbClass>> = wordDao.readAllWords()
    suspend fun addWord(word:dbClass){
        wordDao.addWord(word)
    }
    suspend fun deleteWords(){
        wordDao.deleteAllWords()
    }
    suspend fun deleteWord(word:dbClass){
        wordDao.deleteWord(word)
    }




}