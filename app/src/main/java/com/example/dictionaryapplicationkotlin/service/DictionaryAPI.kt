package com.example.dictionaryapplicationkotlin.service

import com.example.dictionaryapplicationkotlin.model.DictionaryModel
import com.example.dictionaryapplicationkotlin.util.Constant.SECOND_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {
    @GET(SECOND_URL)
    suspend fun getDictionary(@Path("world") world:String):Response<List<DictionaryModel>>


}