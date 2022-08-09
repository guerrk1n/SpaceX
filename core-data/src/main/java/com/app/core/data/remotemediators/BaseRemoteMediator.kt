package com.app.core.data.remotemediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.app.core.database.dao.RemoteKeysDao
import com.app.core.database.model.BaseEntity
import com.app.core.database.model.RemoteKeysEntity

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<T : BaseEntity>(
    private val remoteKeysDao: RemoteKeysDao,
) : RemoteMediator<Int, T>() {

    internal suspend fun getRemoteKeyForLastItem(state: PagingState<Int, T>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                remoteKeysDao.remoteKeysRepoId(item.id)
            }
    }

    internal suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, T>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                remoteKeysDao.remoteKeysRepoId(item.id)
            }
    }

    internal suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, T>,
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { item ->
            state.closestItemToPosition(item)?.id?.let { itemId ->
                remoteKeysDao.remoteKeysRepoId(itemId)
            }
        }
    }
}

internal const val STARTING_PAGE_INDEX = 1
internal const val REMOTE_MEDIATOR_CACHE_TIMEOUT_IN_HOURS = 24L