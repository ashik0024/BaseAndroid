package com.example.appdemo.common

import androidx.core.os.bundleOf
import androidx.paging.PagingSource
import androidx.paging.PagingState

class BaseNetworkPagingSource<T: Any>(
    private val service: BaseApiService<T>,
    private val limit: Int,
    private val offset: Int
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: offset
        return try {
            val data = service.loadData(page, limit)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - limit,
                nextKey = if (data.size < limit) null else page + limit
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}

