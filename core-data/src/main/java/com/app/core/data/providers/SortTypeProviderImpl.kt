package com.app.core.data.providers

import kotlinx.coroutines.flow.Flow

interface SortTypeProvider<T> {
    suspend fun getSortType(): T

    suspend fun saveSortType(sortType: T)

    fun getSortTypeFlow(): Flow<T>
}