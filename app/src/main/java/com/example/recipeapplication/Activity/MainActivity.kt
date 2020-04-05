 package com.example.recipeapplication.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeapp.Fragments.Home
import com.example.recipeapp.Utility.Utility
import com.example.recipeapplication.R
import com.example.recipeapplication.Util.replaceFragment

 class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utility.hideMobileStatusBar(activity =this@MainActivity)
        replaceFragment(activity =this@MainActivity, fragment = Home())
    }



}
