package com.example.dictionaryapplicationkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapplicationkotlin.model.DictionaryModel
import com.example.dictionaryapplicationkotlin.util.Constant.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class mainViewModel:ViewModel() {
    val loading=MutableLiveData<Boolean>()
    val ListOfDatas=MutableLiveData<List<DictionaryModel>>()
     fun getDict(word:String){
        viewModelScope.launch(Dispatchers.Main) {
            val datas=retrofit.getDictionary(word)
                loading.value=true
                if(datas.isSuccessful){
                    datas.body()?.let {
                        if(it.size==0||it.size>0){
                            ListOfDatas.value=it
                            loading.value=false
                        }

                    }
                }
        }
    }
}