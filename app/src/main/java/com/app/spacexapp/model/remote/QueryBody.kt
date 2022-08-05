package com.app.spacexapp.model.remote

data class QueryBody(val options: Options)

data class Options(
    val page: Int,
    val limit: Int,
    val sort: Map<String, String>? = null,
)