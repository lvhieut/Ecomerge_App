package com.example.my_ecomerge_app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.model.SearchHistory

class AdapterHistory(private var listHistorySearch : MutableList<SearchHistory>, private var context: Context):
    RecyclerView.Adapter<AdapterHistory.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHistorySearch.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listHistorySearch = listHistorySearch[position]
        holder.tvHistorySeach.text = listHistorySearch.searchKeyword
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvHistorySeach: TextView = itemView.findViewById(R.id.tvHistorySearch)
    }
}