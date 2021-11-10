package com.unsplash.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unsplash.model.Unsplash

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<Unsplash>)

    @Query(
        "SELECT * FROM unsplash"
    )
    fun selectAll(): PagingSource<Int, Unsplash>

    @Query("DELETE FROM unsplash")
    suspend fun clearRepos()
}