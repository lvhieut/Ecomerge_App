package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.my_ecomerge_app.model.SearchHistory

@Dao
interface SearchHistoryDao {
    @Insert
    suspend fun insert(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_history ORDER BY id DESC")
    fun getAllSearchHistory(): List<SearchHistory>

    @Query("DELETE FROM search_history")
    suspend fun deleteAll()
}