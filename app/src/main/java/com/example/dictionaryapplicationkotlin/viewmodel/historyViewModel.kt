package com.example.dictionaryapplicationkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapplicationkotlin.adapter.wordAdapter
import com.example.dictionaryapplicationkotlin.db.WordDatabase
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.repository.wordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class historyViewModel(application: Application):AndroidViewModel(application) {
    val readAllWordsFromVM : LiveData<List<dbClass>>
    val repository:wordRepository
    init {
       val wordDao= WordDatabase.getDatabase(application).wordDao()
        repository= wordRepository(wordDao)
        readAllWordsFromVM=repository.readAllWords
    }
    fun addWordVM(word:dbClass){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(word)
        }
    }
    fun deleteAllWords(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWords()
        }
    }
    fun deleteNotes(word:dbClass){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(word)
        }
    }

}