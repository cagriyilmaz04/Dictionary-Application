package com.example.dictionaryapplicationkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.service.DictionaryDao
@Database(entities = [dbClass::class],version = 1,exportSchema = false)
abstract class WordDatabase:RoomDatabase(){
    abstract fun wordDao():DictionaryDao
    companion object{
        @Volatile
        private var INSTANCE:WordDatabase?=null

        fun getDatabase(context: Context):WordDatabase{
            val temp= INSTANCE
            if(temp!=null){
                return temp
            }
            synchronized(this){
                var instance= Room.databaseBuilder(context,WordDatabase::class.java,"new_all_words_list").build()
                INSTANCE=instance
                return instance
            }
        }
    }
}