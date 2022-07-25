package com.example.spacexapp.ui.screens.maintabs

data class MappedDataWithPagingInfo<T: Any>(
    val data: List<T>,
    val totalPages: Int,
    val page: Int
)