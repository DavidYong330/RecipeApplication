package com.example.recipeapp.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recipeapp.Object.RecipeObject
import com.example.recipeapp.Utility.Constant
import com.example.recipeapplication.R


class RecipeDetail : Fragment() {
    private var llRecipeImage: LinearLayout? = null
    private var tvRecipeName: TextView? = null
    private var tvTimeComplete: TextView? = null
    private var tvIngredients: TextView? = null
    private var tvSteps: TextView? = null
    private var recipeObject: RecipeObject? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        if (arguments != null) {
            if (arguments!!.containsKey(Constant.RECIPE_LIST)) {
                recipeObject = arguments!!.getSerializable(Constant.RECIPE_LIST) as RecipeObject
            }
        }
        findViewByIdsAndSetListeners(rootView)
        setUpDetails(recipeObject!!)
        return rootView

    }

    private fun findViewByIdsAndSetListeners(rootView: View) {
        llRecipeImage = rootView.findViewById(R.id.llRecipeImage)
        tvRecipeName = rootView.findViewById(R.id.tvRecipeName)
        tvTimeComplete = rootView.findViewById(R.id.tvTimeComplete)
        tvIngredients = rootView.findViewById(R.id.tvIngredients)
        tvSteps = rootView.findViewById(R.id.tvSteps)

    }

    private fun setUpDetails(recipeObject: RecipeObject) {
        llRecipeImage!!.background = recipeObject.recipeImage
        tvRecipeName!!.text = recipeObject.recipeName
        tvTimeComplete!!.text = recipeObject.timeComplete
        tvIngredients!!.text = recipeObject.recipeIngredients
        tvSteps!!.text = recipeObject.recipeDirections
    }

}