package com.example.dictionaryapplicationkotlin.util

import android.view.View

object ExtensionFunctions {
    fun View.hide(){
        this.visibility=View.INVISIBLE
    }
    fun View.show(){
        this.visibility=View.VISIBLE
    }



}