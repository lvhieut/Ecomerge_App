package com.example.my_ecomerge_app.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.my_ecomerge_app.model.SearchHistory

@Dao
interface SearchHistoryDao {
    @Insert
    suspend fun insert(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_history ORDER BY id")
    fun getAllSearchHistory(): List<SearchHistory>

    @Query("DELETE FROM search_history")
    suspend fun deleteAll()

    @Query("DELETE FROM search_history WHERE id = :searchId")
    suspend fun deleteSearchHistoryById(searchId: Long)

    @Update
    suspend fun updateSearchHistory(searchHistory: SearchHistory)
}