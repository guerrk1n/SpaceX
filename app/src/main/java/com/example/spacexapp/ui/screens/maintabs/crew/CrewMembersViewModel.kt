package com.example.spacexapp.ui.screens.maintabs.crew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spacexapp.api.SpaceXService
import com.example.spacexapp.ui.screens.maintabs.BasePagingSource
import com.example.spacexapp.ui.screens.maintabs.MappedDataWithPagingInfo
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMember
import com.example.spacexapp.ui.screens.maintabs.crew.member.CrewMemberMapper
import com.example.spacexapp.util.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class CrewMembersViewModel(
    private val spaceXService: SpaceXService,
    private val mapper: CrewMemberMapper,
) : ViewModel() {

    private val pager: Pager<Int, CrewMember> =
        Pager(config = PagingConfig(Constants.PAGE_SIZE), pagingSourceFactory = ::initPagingSource)

    val crew: Flow<PagingData<CrewMember>> = pager.flow.cachedIn(viewModelScope)

    private fun initPagingSource() = BasePagingSource() {
        viewModelScope.async {
            val response = spaceXService.getCrewMembers(it)
            val crewMembers = response.crewMembers.map(mapper::map)
            val totalPages = response.totalPages
            val page = response.page
            MappedDataWithPagingInfo(crewMembers, totalPages, page)
        }

    }
}