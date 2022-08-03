package com.example.spacexapp.ui.screens.maintabs.rockets

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spacexapp.data.repository.RocketsRepository
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketDboMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RocketsViewModel(
    private val mapper: RocketDboMapper,
    rocketsRepository: RocketsRepository,
) : ViewModel() {

    val rockets: Flow<PagingData<Rocket>> = rocketsRepository.getRocketsStream().map { pagingData ->
        pagingData.map(mapper::map)
    }
}