package com.example.recipeapplication.Util

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipeapplication.R


 fun replaceFragment(activity : AppCompatActivity, fragment: Fragment) {
    val transaction = activity.supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, fragment)
    transaction.commit()
}