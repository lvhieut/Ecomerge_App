package com.example.my_ecomerge_app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.model.CategorySpinner

class CategoryAdapter(context: Context, resource : Int, categoryList: List<CategorySpinner>): ArrayAdapter<CategorySpinner>(context, 0 ,categoryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_selected, parent, false)
        val category = getItem(position)

        val tvSpinner = itemView.findViewById<TextView>(R.id.spAndroid)
        tvSpinner.text = category?.name

        return itemView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var updatedConvertView = convertView
        updatedConvertView = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        val tvCategory = convertView?.findViewById(R.id.tvCategory) as TextView
        val category : CategorySpinner? = this.getItem(position)

        if (category!= null){
            tvCategory.text = category.name
        }

        return updatedConvertView
    }



}