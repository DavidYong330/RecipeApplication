package com.example.recipeapp.Object

import android.graphics.drawable.Drawable
import java.io.Serializable

class RecipeObject : Serializable {

    var recipeName: String? = null
    var level: String? = null
    var timeComplete: String? = null
    var recipeImage: Drawable? = null
    var recipeTypes: String? = null
    var recipeIngredients: String? = null
    var recipeDirections: String? = null
    var selfCreatedIndicator: String? = null
    var image: ByteArray? = null


}
