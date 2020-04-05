package com.example.recipeapp.Fragments


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.recipeapp.Object.RecipeObject
import com.example.recipeapp.SQLiteDB.DBHelper
import com.example.recipeapp.Utility.AlertTool
import com.example.recipeapp.Utility.Constant
import com.example.recipeapp.Utility.SpinnerTool
import com.example.recipeapp.Utility.Utility
import android.app.Activity.RESULT_OK
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.recipeapp.Utility.Utility.popStack
import com.example.recipeapplication.R
import java.io.ByteArrayOutputStream
import java.util.*


class CreateOwnRecipe(editRecipe: Boolean, private val recipeObject: RecipeObject?) : Fragment(), View.OnClickListener, AlertTool.alertListener {
    private var llCaptureImage: LinearLayout? = null
    private var tvTitle: TextView? = null
    private var etCompletionTime: EditText? = null
    private var etIngredients: EditText? = null
    private var etSteps: EditText? = null
    private var etRecipeName: EditText? = null
    private var spDifficulty: Spinner? = null
    private var spRecipeType: Spinner? = null
    private var btnCreateRecipe: Button? = null
    private var Image: ByteArray? = null
    private var dbHelper: DBHelper? = null
    private var alertTool: AlertTool? = null
    private var spinnerTool: SpinnerTool? = null
    private var hasCapturedImage = false
    private var editRecipe = false


    private val editedRecipe: RecipeObject
        get() {
            val recipeObject = RecipeObject()
            val Imagebyte = Utility.drawableToByte(llCaptureImage!!.background)
            recipeObject.image = Imagebyte
            val recipeName = etRecipeName!!.text.toString().trim { it <= ' ' }
            val completionTime = etCompletionTime!!.text.toString().trim { it <= ' ' }
            val difficulty = spDifficulty!!.selectedItem.toString().trim { it <= ' ' }
            val ingredients = etIngredients!!.text.toString().trim { it <= ' ' }
            val steps = etSteps!!.text.toString().trim { it <= ' ' }
            val type = spRecipeType!!.selectedItem.toString()
            val selfCreatedindicator = "yes"
            recipeObject.recipeName = recipeName
            recipeObject.recipeTypes = type
            recipeObject.selfCreatedIndicator = selfCreatedindicator
            recipeObject.timeComplete = completionTime
            recipeObject.level = difficulty
            recipeObject.recipeIngredients = ingredients
            recipeObject.recipeDirections = steps

            return recipeObject


        }

    init {
        this.editRecipe = editRecipe
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_create_own_recipe, container, false)
        dbHelper = DBHelper(Objects.requireNonNull<FragmentActivity>(activity))
        alertTool = AlertTool(this)
        spinnerTool = SpinnerTool()
        findViewByIdsAndSetListeners(rootView)
        if (recipeObject != null) {
            setUpEditRecipeScreen(recipeObject)
        }
        spinnerTool!!.setSpinner(Objects.requireNonNull<FragmentActivity>(activity), resources.getStringArray(R.array.selection_difficulty_array), spDifficulty!!)
        spinnerTool!!.setSpinner(Objects.requireNonNull<FragmentActivity>(activity), resources.getStringArray(R.array.selection_recipe_types_array), spRecipeType!!)


        return rootView

    }

    private fun findViewByIdsAndSetListeners(rootView: View) {
        tvTitle = rootView.findViewById(R.id.tvTitle)
        llCaptureImage = rootView.findViewById(R.id.ivCaptureImage)
        etCompletionTime = rootView.findViewById(R.id.etCompletionTime)
        spDifficulty = rootView.findViewById(R.id.spDifficulty)
        spRecipeType = rootView.findViewById(R.id.spRecipeType)
        etIngredients = rootView.findViewById(R.id.etIngredients)
        etSteps = rootView.findViewById(R.id.etSteps)
        btnCreateRecipe = rootView.findViewById(R.id.btnCreateRecipe)
        etRecipeName = rootView.findViewById(R.id.etRecipeName)

        llCaptureImage!!.setOnClickListener(this)
        btnCreateRecipe!!.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivCaptureImage -> dispatchTakePictureIntent()

            R.id.btnCreateRecipe -> if (editRecipe) {
                alertTool!!.alertDuoButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.EDIT_RECIPE_DIALOG, R.string.update_recipe, R.string.yes, R.string.no)
            } else {
                validation()
            }
        }
    }

    private fun setUpEditRecipeScreen(recipeObject: RecipeObject) {
        if (editRecipe) {
            tvTitle!!.text = "Edit Recipe"
            llCaptureImage!!.background = recipeObject.recipeImage
            adjustLayoutSize(llCaptureImage!!)
            etRecipeName!!.setText(recipeObject.recipeName)
            etCompletionTime!!.setText(recipeObject.timeComplete)
            setSpinnerValueWhenEdit()
            etIngredients!!.setText(recipeObject.recipeIngredients)
            etSteps!!.setText(recipeObject.recipeDirections)
            btnCreateRecipe!!.text = "Update Recipe"
            hasCapturedImage = true
        }

    }


    private fun setSpinnerValueWhenEdit() {
        val level = recipeObject?.level
        when (level) {
            "Easy" -> spDifficulty!!.setSelection(0)
            "Medium" -> spDifficulty!!.setSelection(1)
            "Hard" -> spDifficulty!!.setSelection(2)
        }
    }

    private fun adjustLayoutSize(linearLayout: LinearLayout) {
        val layoutParams = LinearLayout.LayoutParams(800, 600, Gravity.CENTER.toFloat())
        linearLayout.layoutParams = layoutParams
    }

    // Open Camera Function
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(takePictureIntent, Constant.TAKE_PHOTO)
        }
    }

    // Validation For Recipe Creation
    private fun validation() {
        if (etRecipeName!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            alertTool!!.alertSingleButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.VALIDATE_NAME, R.string.validate_recipe_name, R.string.ok)
        } else if (!hasCapturedImage) {
            alertTool!!.alertSingleButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.VALIDATE_PHOTO, R.string.validate_image, R.string.ok)
        } else if (etCompletionTime!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            alertTool!!.alertSingleButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.VALIDATE_COMPLETION_TIME, R.string.validate_completion_time, R.string.ok)
        } else if (spDifficulty!!.selectedItem.toString().trim { it <= ' ' }.isEmpty()) {
            alertTool!!.alertSingleButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.VALIDATE_DIFFICULTY, R.string.validate_difficulty, R.string.ok)
        } else if (etIngredients!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            alertTool!!.alertSingleButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.VALIDATE_INGREDIENTS, R.string.validate_ingredients, R.string.ok)
        } else if (etSteps!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            alertTool!!.alertSingleButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.VALIDATE_STEPS, R.string.validate_steps, R.string.ok)
        } else {
            alertTool!!.alertDuoButton(Objects.requireNonNull<FragmentActivity>(activity), Constant.CREATE_RECIPE_DIALOG, R.string.create_new_recipe, R.string.yes, R.string.no)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.TAKE_PHOTO && resultCode == RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            //Convert to Drawable First
            Utility.bitmap2Drawable(Resources.getSystem(),imageBitmap)
            llCaptureImage!!.background = Utility.bitmap2Drawable(Resources.getSystem(),imageBitmap)
            adjustLayoutSize(llCaptureImage!!)
            hasCapturedImage = true
            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            Image = byteArray
        }
    }

    /** Actions when Click Positive Button On Dialog  */
    override fun onPositiveDialogClick(dialogTag: String) {
        when (dialogTag) {
            Constant.CREATE_RECIPE_DIALOG -> {
                val completionTime = etCompletionTime!!.text.toString().trim { it <= ' ' }
                val difficulty = spDifficulty!!.selectedItem.toString().trim { it <= ' ' }
                val ingredients = etIngredients!!.text.toString().trim { it <= ' ' }
                val steps = etSteps!!.text.toString().trim { it <= ' ' }
                val recipeName = etRecipeName!!.text.toString().trim { it <= ' ' }
                val type = spRecipeType!!.selectedItem.toString()
                val selfCreatedindicator = "yes"

                val insertArr = arrayOf(recipeName, type, completionTime, difficulty, ingredients, steps, selfCreatedindicator)
                dbHelper!!.insertRecipeInfo(insertArr, Image)
                Toast.makeText(context, "Recipe Created Successfully! Yum Yum", Toast.LENGTH_SHORT)
                        .show()
                Home::class.simpleName?.let { popStack(activity = Objects.requireNonNull<FragmentActivity>(activity) as AppCompatActivity,tag = it) }
            }
            Constant.VALIDATE_PHOTO -> {
            }
            Constant.VALIDATE_NAME -> etRecipeName!!.requestFocus()
            Constant.VALIDATE_COMPLETION_TIME -> etCompletionTime!!.requestFocus()
            Constant.VALIDATE_DIFFICULTY -> spDifficulty!!.requestFocus()
            Constant.VALIDATE_INGREDIENTS -> {
            }
            Constant.VALIDATE_STEPS -> etSteps!!.requestFocus()
            Constant.EDIT_RECIPE_DIALOG -> {
                val selectedRecipeName = recipeObject?.recipeName
                if (selectedRecipeName != null) {
                    dbHelper!!.updateDatabase(editedRecipe, selectedRecipeName)
                }
                ViewRecipe::class.simpleName?.let { popStack(activity = Objects.requireNonNull<FragmentActivity>(activity) as AppCompatActivity,tag = it) }
            }
        }
    }

    override fun onNegativeDialogClick(dialogTag: String) {}

}
