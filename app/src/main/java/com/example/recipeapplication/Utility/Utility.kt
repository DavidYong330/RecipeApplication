package com.example.recipeapp.Utility

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ActionMode
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.example.recipeapplication.BuildConfig

import java.io.ByteArrayOutputStream

object Utility {
    /** Method used to hide mobile top status bar  */
    fun hideMobileStatusBar(activity: AppCompatActivity) {
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /** Method used to replace fragment  */
    fun replaceFragment(activity: AppCompatActivity, frameId: Int, transitionFragment: Fragment, fragmentTag: String) {
        val manager = activity.supportFragmentManager
        manager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(frameId, transitionFragment)
                .addToBackStack(fragmentTag)
                .commit()
    }

    fun popStack(activity: AppCompatActivity,tag: String) {
        val isPopped = activity.supportFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        if (!isPopped) {
            activity.supportFragmentManager.popBackStack()
        }
    }

    /**      Convert bitmap to Drawable  */
    fun bitmap2Drawable(resources: Resources,bitmap: Bitmap): Drawable {
        return BitmapDrawable(resources,bitmap)
    }

    fun drawableToByte(drawable: Drawable): ByteArray {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }


}
