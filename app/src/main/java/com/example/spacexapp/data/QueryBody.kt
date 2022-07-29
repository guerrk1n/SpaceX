package com.example.spacexapp.data

data class QueryBody(val options: Options)

data class Options(
    val page: Int,
    val limit: Int,
    val sort: Map<String, String>? = null,
)