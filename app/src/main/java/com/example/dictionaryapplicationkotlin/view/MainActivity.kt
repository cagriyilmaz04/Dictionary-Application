package com.example.dictionaryapplicationkotlin.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dictionaryapplicationkotlin.R
import com.example.dictionaryapplicationkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController=findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
        sharedPref=this.getSharedPreferences("com.example.dictionaryapplicationkotlin.view",Context.MODE_PRIVATE)


    }

    override fun onStop() {
        sharedPref.edit().putString("word",null).apply()
        super.onStop()
    }

    override fun onDestroy() {
        sharedPref.edit().putString("word",null).apply()
        super.onDestroy()
    }

}