package com.example.recipeapp.Fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.recipeapp.Utility.Utility
import com.example.recipeapplication.R

import java.util.Objects

class Home : Fragment(), View.OnClickListener {
    private var rlViewRecipe: RelativeLayout? = null
    private var rlCreateRecipe: RelativeLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        findViewByIdsAndSetListeners(rootView)
        return rootView

    }


    private fun findViewByIdsAndSetListeners(rootView: View) {
        rlViewRecipe = rootView.findViewById(R.id.rlViewRecipe)
        rlCreateRecipe = rootView.findViewById(R.id.rlCreateRecipe)
        rlViewRecipe!!.setOnClickListener(this)
        rlCreateRecipe!!.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(view: View) {
        when (view.id) {
            R.id.rlViewRecipe -> {
                val viewRecipe = ViewRecipe()
                Utility.replaceFragment(Objects.requireNonNull<FragmentActivity>(activity) as AppCompatActivity, R.id.container, viewRecipe, ViewRecipe::class.java!!.getSimpleName())
            }
            R.id.rlCreateRecipe -> {
                val createOwnRecipe = CreateOwnRecipe(false,null)
                Utility.replaceFragment(Objects.requireNonNull<FragmentActivity>(activity) as AppCompatActivity, R.id.container, createOwnRecipe, CreateOwnRecipe::class.java!!.getSimpleName())
            }
        }
    }
}
