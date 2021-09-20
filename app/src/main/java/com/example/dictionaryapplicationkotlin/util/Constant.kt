package com.example.dictionaryapplicationkotlin.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.dictionaryapplicationkotlin.service.DictionaryAPI
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Constant {
    const val BASE_URL ="https://api.dictionaryapi.dev/"
    const val SECOND_URL ="api/v2/entries/en/{world}"

         val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
                 .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryAPI::class.java)




}