package com.example.my_ecomerge_app.view.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.model.SearchHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterHistory(private var listHistorySearch : MutableList<SearchHistory>, private var context: Context):
    RecyclerView.Adapter<AdapterHistory.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHistorySearch.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historySearch = listHistorySearch[position]
        holder.tvHistorySeach.text = historySearch.searchKeyword

        holder.btnDeleteSearch.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Xoá lịch sử tìm kiếm")
                .setMessage("Bạn có muốn xoá không?")
                .setPositiveButton("Có") { dialog, _ ->
                    // Xoá phần tử khỏi danh sách và cập nhật UI
                    listHistorySearch.removeAt(position)
                    notifyDataSetChanged()
                    // Xoá phần tử từ cơ sở dữ liệu
                    CoroutineScope(Dispatchers.IO).launch {
                        MyApplication.appDatabase.searchHistoryDao().deleteSearchHistoryById(historySearch.id)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Không") { dialog, _ ->

                    CoroutineScope(Dispatchers.IO).launch {

                        MyApplication.appDatabase.searchHistoryDao().updateSearchHistory(historySearch)
                    }
                    dialog.dismiss()
                }
                .create()
                .show()
        }

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvHistorySeach: TextView = itemView.findViewById(R.id.tvHistorySearch)
        val btnDeleteSearch: ImageView = itemView.findViewById(R.id.btnDelete)
    }
}