package com.app.core.data.remotemediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.app.core.database.dao.RemoteKeysDao
import com.app.core.database.model.BaseEntity
import com.app.core.database.model.RemoteKeysEntity
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<T : BaseEntity>(
    private val remoteKeysDao: RemoteKeysDao,
) : RemoteMediator<Int, T>() {

    companion object {
        internal const val STARTING_PAGE_INDEX = 1
        private const val REMOTE_MEDIATOR_CACHE_TIMEOUT_IN_HOURS = 24L
        internal val CACHE_TIMEOUT = TimeUnit.MILLISECONDS.convert(
            REMOTE_MEDIATOR_CACHE_TIMEOUT_IN_HOURS,
            TimeUnit.HOURS
        )
    }

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

    internal fun getInitializeAction(createdTime: Long?): InitializeAction {
        val isCacheTimeout = createdTime?.let { it >= CACHE_TIMEOUT } ?: true
        return if (isCacheTimeout) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    internal suspend fun getPageForRefreshLoadType(state: PagingState<Int, T>): Int {
        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
        return remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, T>,
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { item ->
            state.closestItemToPosition(item)?.id?.let { itemId ->
                remoteKeysDao.remoteKeysRepoId(itemId)
            }
        }
    }
}