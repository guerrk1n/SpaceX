package com.example.spacexapp.ui.screens.maintabs

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spacexapp.data.Options
import com.example.spacexapp.data.QueryBody
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.Deferred

class BasePagingSource<T : Any>(
    private val loadData: (QueryBody) -> Deferred<MappedDataWithPagingInfo<T>>,
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1
            val options = Options(nextPage, Constants.PAGE_SIZE)
            val queryBody = QueryBody(options)
            val mappedData = loadData.invoke(queryBody).await()
            val data = mappedData.data
            val totalPages = mappedData.totalPages
            val page = mappedData.page
            LoadResult.Page(
                data,
                if (nextPage == 1) null else nextPage - 1,
                if (nextPage >= totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }
}