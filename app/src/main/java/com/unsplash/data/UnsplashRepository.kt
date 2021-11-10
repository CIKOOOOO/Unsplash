package com.unsplash.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.unsplash.api.UnsplashService
import com.unsplash.db.UnsplashDatabase
import com.unsplash.model.Unsplash
import kotlinx.coroutines.flow.Flow

class UnsplashRepository(
    private val service: UnsplashService,
    private val database: UnsplashDatabase
) {
    fun getSearchResultStream(query: String): Flow<PagingData<Unsplash>> {
        val pagingSourceFactory =  { database.unsplashDao().selectAll()}
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = GithubRemoteMediator(
                query,
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}