package com.app.core.data.providers.search

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchQueryProvider @Inject constructor() {

    var queryMap: MutableMap<String, String> = mutableMapOf()
}