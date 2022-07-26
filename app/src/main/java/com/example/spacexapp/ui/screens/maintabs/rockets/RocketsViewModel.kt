package com.example.spacexapp.ui.screens.maintabs.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spacexapp.api.SpaceXService
import com.example.spacexapp.ui.screens.maintabs.BasePagingSource
import com.example.spacexapp.ui.screens.maintabs.MappedDataWithPagingInfo
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class RocketsViewModel(
    private val spaceXService: SpaceXService,
    private val mapper: RocketMapper,
) : ViewModel() {

    private val pager: Pager<Int, Rocket> =
        Pager(config = PagingConfig(Constants.PAGE_SIZE), pagingSourceFactory = ::initPagingSource)

    val rockets: Flow<PagingData<Rocket>> = pager.flow.cachedIn(viewModelScope)

    private fun initPagingSource() = BasePagingSource() {
        viewModelScope.async {
            val response = spaceXService.getRockets(it)
            val crewMembers = response.rockets.map(mapper::map)
            val totalPages = response.totalPages
            val page = response.page
            MappedDataWithPagingInfo(crewMembers, totalPages, page)
        }
    }
}