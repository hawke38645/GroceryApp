package com.hawke38645.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hawke38645.groceryapp.R
import com.hawke38645.groceryapp.activities.SubCategoryActivity
import com.hawke38645.groceryapp.app.Config
import com.hawke38645.groceryapp.models.Category
import com.hawke38645.groceryapp.models.Category.Companion.KEY_CAT_ID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class AdapterCategory(var mContext: Context): RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    private var mList: ArrayList<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = mList[position]
        holder.bindView(category)
    }

    fun setData(list: ArrayList<Category>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(category: Category) {
            itemView.text_view_category_name.text = category.catName
            Picasso
                .get()
                .load(Config.IMAGE_URL + category.catImage)
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.image_view_category_row)
            itemView.setOnClickListener{
                val intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra(KEY_CAT_ID, category.catId)
                mContext.startActivity(intent)
            }
        }

    }
}