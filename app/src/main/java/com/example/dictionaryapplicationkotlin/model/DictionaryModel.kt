package com.example.dictionaryapplicationkotlin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class DictionaryModel (
            val word:String?=null,
            val phonetic:String?=null,
            val phonetics:List<text>?=null,
            val origin:String?=null,
            val meanings: List<Detail>?=null, )
data class Detail(val partOfSpeech:String?=null, val definitions:List<DetailDef>?=null)

data class DetailDef(val definition:String?=null, val example:String?=null,
                     val synonyms:List<String>?=null, val antonyms:List<String>?=null)
data class text(val text:String?=null, val audio:String?=null)

@Parcelize
data class sendingModel(val word:String?=null, val partOfSpeech: String?=null, val detailDef:@RawValue DetailDef?=null):Parcelable


@Entity(tableName = "word_room_db")
@Parcelize
data class dbClass(
    val string: String? = null

):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}