package com.example.recipeapp.SQLiteDB

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import com.example.recipeapp.Object.RecipeObject
import com.example.recipeapp.Utility.Utility


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val allCreatedRecipes: ArrayList<RecipeObject>
        get() {
            val db = writableDatabase
            val allCreatedRecipes = ArrayList<RecipeObject>()

            val cursor = db.rawQuery("select * from app_user", null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val recipeObject = RecipeObject()
                    recipeObject.recipeName = cursor.getString(0)
                    recipeObject.recipeTypes = cursor.getString(1)
                    recipeObject.timeComplete = cursor.getString(2)
                    recipeObject.level = cursor.getString(3)
                    recipeObject.recipeIngredients = cursor.getString(4)
                    recipeObject.recipeDirections = cursor.getString(5)
                    val bmp = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).size)
                    recipeObject.recipeImage = Utility.bitmap2Drawable(Resources.getSystem(),bmp)
                    recipeObject.selfCreatedIndicator = cursor.getString(7)
                    allCreatedRecipes.add(recipeObject)
                    cursor.moveToNext()

                }
            }
            return allCreatedRecipes
        }

    private fun initDatabase(db: SQLiteDatabase) {

        /**
         * Create user information table
         */
        db.execSQL("CREATE TABLE IF NOT EXISTS app_user(" +
                "name TEXT," +
                "type TEXT ," +
                "timecomplete TEXT, " +
                "level TEXT, " +
                "ingredients TEXT, " +
                "directions TEXT, " +
                "image BLOB, " +
                "selfcreated TEXT" +
                ");")

    }

    /**
     * Insert user information into app_user table
     */
    fun insertRecipeInfo(userInfo: Array<String>, image: ByteArray?) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("name", userInfo[0])
        values.put("type", userInfo[1])
        values.put("timecomplete", userInfo[2])
        values.put("level", userInfo[3])
        values.put("ingredients", userInfo[4])
        values.put("directions", userInfo[5])
        values.put("image", image)
        values.put("selfcreated", userInfo[6])

        db.insert("app_user", null, values)
        db.close()
    }

    /**
     * Query all user information into app_user table
     */
    fun fetchRecipeInfo(): RecipeObject? {
        val db = writableDatabase
        var recipe: RecipeObject? = null

        val c = db.rawQuery("SELECT * FROM app_user ", null)
        if (c.moveToNext()) {
            recipe = RecipeObject()
            recipe.recipeName = c.getString(0)
            recipe.recipeTypes = c.getString(1)
            recipe.timeComplete = c.getString(2)
            recipe.level = c.getString(3)
            recipe.recipeIngredients = c.getString(4)
            recipe.recipeDirections = c.getString(5)
            val bmp = BitmapFactory.decodeByteArray(c.getBlob(6), 0, c.getBlob(6).size)
            recipe.recipeImage = Utility.bitmap2Drawable(Resources.getSystem(),bmp)

        }
        c.close()
        db.close()
        return recipe
    }

    fun updateDatabase(recipeObject: RecipeObject, selectedRecipeName: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("name", recipeObject.recipeName)
        cv.put("type", recipeObject.recipeTypes)
        cv.put("timecomplete", recipeObject.timeComplete)
        cv.put("level", recipeObject.level)
        cv.put("ingredients", recipeObject.recipeIngredients)
        cv.put("directions", recipeObject.recipeDirections)
        cv.put("image", recipeObject.image)
        cv.put("selfcreated", recipeObject.selfCreatedIndicator)
        db.update("app_user", cv, "name = ?", arrayOf(selectedRecipeName))
        db.close()

    }

    fun deleteRow(id: String) {
        val where = "name" + " = ?"
        val whereArgs = arrayOf(id)

        val db = writableDatabase
        db.delete("app_user", where, whereArgs)
    }


    override fun onCreate(db: SQLiteDatabase) {
        initDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        initDatabase(db)
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "database.db"
    }
}

