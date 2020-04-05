package com.example.recipeapp.Fragments


import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Adapter.RecipeSelectionAdapter
import com.example.recipeapp.Object.RecipeObject
import com.example.recipeapp.SQLiteDB.DBHelper
import com.example.recipeapp.Utility.AlertTool
import com.example.recipeapp.Utility.Constant
import com.example.recipeapp.Utility.Utility
import com.example.recipeapplication.R
import java.util.*


class ViewRecipe : Fragment(), RecipeSelectionAdapter.RecipeSelectionAdapterListener, AlertTool.alertListener {


    private var rvRecipes: RecyclerView? = null
    private var recipeSelectionAdapter: RecipeSelectionAdapter? = null
    private var spFilterRecipe: Spinner? = null
    private var dbHelper: DBHelper? = null
    private var alertTool: AlertTool? = null
    private var selectedRecipe: String? = null
    private var selectedRecipeObj: RecipeObject? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_view_recipe, container, false)
        findViewByIdsAndSetListeners(rootView)
        dbHelper = DBHelper(Objects.requireNonNull<FragmentActivity>(activity))
        alertTool = AlertTool(this)
        setRecipesAdapter(setRecipe())
        setSpinner()
        return rootView

    }

    private fun findViewByIdsAndSetListeners(rootView: View) {
        rvRecipes = rootView.findViewById(R.id.rvRecipes)
        spFilterRecipe = rootView.findViewById(R.id.spFilterRecipe)

    }

    private fun setRecipesAdapter(recipeList: ArrayList<RecipeObject>) {
        val mLayoutManagerSupport = LinearLayoutManager(activity)
        rvRecipes!!.layoutManager = mLayoutManagerSupport
        rvRecipes!!.isNestedScrollingEnabled = false
        recipeSelectionAdapter = RecipeSelectionAdapter(Objects.requireNonNull<FragmentActivity>(activity), recipeList, this)

        rvRecipes!!.adapter = recipeSelectionAdapter
    }


    private fun setSpinner() {
        val spinnerArrayAdapter = object : ArrayAdapter<String>(
                context!!, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.selection_recipe_array)) {

            override fun getDropDownView(position: Int, convertView: View?,
                                         parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                tv.gravity = Gravity.CENTER
                tv.setTextColor(Color.BLACK)

                return view
            }

        }
        spFilterRecipe!!.adapter = spinnerArrayAdapter
        spFilterRecipe!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> filterRecipeType("all")
                    1 -> filterRecipeType("Chicken")
                    2 -> filterRecipeType("Fish")
                    3 -> filterRecipeType("Others")
                }
         }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }


    /**             Filter the recipelist based on spinner selection            */
    fun filterRecipeType(filterRecipe: String) {
        var filteredRecipe = ArrayList<RecipeObject>()
        when (filterRecipe) {
            "all" -> {
                filteredRecipe = setRecipe()
                setRecipesAdapter(filteredRecipe)
            }


            "Chicken" -> {
                for (i in 0 until setRecipe().size) {
                    if (setRecipe()[i].recipeTypes == "Chicken") {
                        filteredRecipe.add(setRecipe()[i])
                    }
                }
                setRecipesAdapter(filteredRecipe)
            }

            "Fish" -> {
                for (i in 0 until setRecipe().size) {
                    if (setRecipe()[i].recipeTypes == "Fish") {
                        filteredRecipe.add(setRecipe()[i])
                    }
                }
                setRecipesAdapter(filteredRecipe)
            }
            "Others" -> {
                for (i in 0 until setRecipe().size) {
                    if (setRecipe()[i].recipeTypes == "Others") {
                        filteredRecipe.add(setRecipe()[i])
                    }
                }
                setRecipesAdapter(filteredRecipe)
            }
        }

    }


    /**             This is the List of recipe object               */
    private fun setRecipe(): ArrayList<RecipeObject> {
        val recipeList = ArrayList<RecipeObject>()
        val recipeObject = RecipeObject()
        recipeObject.recipeName = "Fried Chicken"
        recipeObject.level = "Easy"
        recipeObject.selfCreatedIndicator = "no"
        recipeObject.recipeIngredients = resources.getString(R.string.ingredients_fried_chicken)
        val drawable1: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.chicken_fried_chicken, null)
        recipeObject.recipeImage = drawable1
        recipeObject.recipeDirections = resources.getString(R.string.steps_fried_chicken)
        recipeObject.timeComplete = "60"
        recipeObject.recipeTypes = "Chicken"
        val recipeObject2 = RecipeObject()
        recipeObject2.recipeName = "Chicken Sausage With Peppers"
        recipeObject2.level = "Medium"
        recipeObject2.selfCreatedIndicator = "no"
        val drawable2: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.chicken_chicken_sausage, null)
        recipeObject2.recipeImage = drawable2
        recipeObject2.recipeIngredients = resources.getString(R.string.ingredients_chicken_sausage)
        recipeObject2.recipeDirections = resources.getString(R.string.steps_chicken_sausage)
        recipeObject2.timeComplete = "35"
        recipeObject2.recipeTypes = "Chicken"
        val recipeObject3 = RecipeObject()
        recipeObject3.recipeName = "Curry Salmon With Mango Chutney"
        recipeObject3.level = "Hard"
        recipeObject3.selfCreatedIndicator = "no"
        val drawable3: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.chicken_chicken_sausage, null)
        recipeObject3.recipeImage = drawable3
        recipeObject3.recipeIngredients = resources.getString(R.string.ingredients_curry_salmon)
        recipeObject3.recipeDirections = resources.getString(R.string.steps_curry_salmon)
        recipeObject3.timeComplete = "40"
        recipeObject3.recipeTypes = "Fish"

        recipeList.add(recipeObject)
        recipeList.add(recipeObject2)
        recipeList.add(recipeObject3)


        val mergeRecipes = ArrayList<RecipeObject>()
        mergeRecipes.addAll(recipeList)
        mergeRecipes.addAll(dbHelper!!.allCreatedRecipes)

        return mergeRecipes
    }

    override fun onDetailBtnClick(recipeList: RecipeObject, position: Int) {
        val recipeDetail = RecipeDetail()
        val bundle = Bundle()
        bundle.putSerializable(Constant.RECIPE_LIST, recipeList)
        recipeDetail.arguments = bundle
        Utility.replaceFragment(Objects.requireNonNull<FragmentActivity>(activity) as AppCompatActivity, R.id.container, recipeDetail, RecipeDetail::class.java.getSimpleName())

    }

    override fun onDeleteBtnClick(recipeObject: RecipeObject) {
        selectedRecipe = recipeObject.recipeName
        alertTool!!.alertDuoButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.CONFIRM_DELETE, R.string.confirm_delete, R.string.yes, R.string.no)
    }

    override fun onEditeBtnClick(recipeObject: RecipeObject) {
        val imageByte = Utility.drawableToByte(recipeObject.recipeImage!!)
        selectedRecipeObj = recipeObject
        selectedRecipeObj!!.image = imageByte
        alertTool!!.alertDuoButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.CONFIRM_EDIT, R.string.confirm_edit, R.string.yes, R.string.no)

    }
    override fun onPositiveDialogClick(dialogTag: String) {
        when (dialogTag) {
            Constant.CONFIRM_DELETE -> {
                selectedRecipe?.let { dbHelper!!.deleteRow(it) }
                setRecipesAdapter(setRecipe())
            }
            Constant.CONFIRM_EDIT -> {
                val createOwnRecipe = selectedRecipeObj?.let { CreateOwnRecipe(true, it) }
                if (createOwnRecipe != null) {
                    Utility.replaceFragment((activity as AppCompatActivity?)!!, R.id.container, createOwnRecipe, CreateOwnRecipe::class.java.getSimpleName())
                }
            }
        }
    }

    override fun onNegativeDialogClick(dialogTag: String) {

    }
}// Required empty public constructor
