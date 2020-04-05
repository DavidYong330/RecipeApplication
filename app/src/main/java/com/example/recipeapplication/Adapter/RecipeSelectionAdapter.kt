package com.example.recipeapp.Adapter

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Adapter.RecipeSelectionAdapter.MyViewHolder
import com.example.recipeapp.Object.RecipeObject
import com.example.recipeapplication.R

class RecipeSelectionAdapter(private val context: Context, private val listOfRecipe: List<RecipeObject>, private val recipeSelectionAdapterListener: RecipeSelectionAdapterListener) : RecyclerView.Adapter<MyViewHolder>() {
    private var mLastClickTime: Long = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRecipeName: TextView
        val tvCompletionTime: TextView
        val tvLevel: TextView
        val llRecipeImage: LinearLayout
        val llActionButtons: LinearLayout
        val llRecipeContainer: LinearLayout
        val ivEdit: ImageView
        val ivDelete: ImageView
        val vDivider: View


        init {
            tvRecipeName = view.findViewById(R.id.tvRecipeName)
            llRecipeImage = view.findViewById(R.id.llRecipeImage)
            llRecipeContainer = view.findViewById(R.id.llRecipeContainer)
            llActionButtons = view.findViewById(R.id.llActionButtons)
            tvCompletionTime = view.findViewById(R.id.tvCompletionTime)
            tvLevel = view.findViewById(R.id.tvLevel)
            ivEdit = view.findViewById(R.id.ivEdit)
            ivDelete = view.findViewById(R.id.ivDelete)
            vDivider = view.findViewById(R.id.vDivider)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_options_selection, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val recipeList = listOfRecipe[position]
        setHolderValues(holder, recipeList, position)
    }


    private fun setHolderValues(holder: MyViewHolder, recipeList: RecipeObject, position: Int) {

        // Only the self created recipe can be edited or delete.
        holder.llActionButtons.visibility = if (recipeList.selfCreatedIndicator == "yes")
            View.VISIBLE
        else
            View.INVISIBLE
        //Hide the last item divider
        holder.vDivider.visibility = if (listOfRecipe.size - 1 == position) View.GONE else View.VISIBLE

        holder.tvRecipeName.text = recipeList.recipeName
        holder.llRecipeImage.background = recipeList.recipeImage
        holder.tvCompletionTime.text = recipeList.timeComplete
        holder.tvLevel.text = recipeList.level
        holder.ivEdit.setOnClickListener { recipeSelectionAdapterListener.onEditeBtnClick(recipeList) }
        holder.ivDelete.setOnClickListener { recipeSelectionAdapterListener.onDeleteBtnClick(recipeList) }
        holder.llRecipeContainer.setOnClickListener(View.OnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@OnClickListener
            }
            recipeSelectionAdapterListener.onDetailBtnClick(recipeList, holder.adapterPosition)
            mLastClickTime = SystemClock.elapsedRealtime()
        })
    }

    override fun getItemCount(): Int {
        return listOfRecipe.size
    }


    interface RecipeSelectionAdapterListener {
        fun onDetailBtnClick(recipeList: RecipeObject, position: Int)
        fun onDeleteBtnClick(recipeObject: RecipeObject)
        fun onEditeBtnClick(recipeObject: RecipeObject)
    }

}

