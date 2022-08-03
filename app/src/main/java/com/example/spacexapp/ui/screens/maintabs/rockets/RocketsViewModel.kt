package com.example.spacexapp.ui.screens.maintabs.rockets

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spacexapp.data.repository.RocketsRepository
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.Rocket
import com.example.spacexapp.ui.screens.maintabs.rockets.rocket.RocketEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RocketsViewModel(
    rocketsRepository: RocketsRepository,
    private val mapper: RocketEntityMapper,
) : ViewModel() {

    val rockets: Flow<PagingData<Rocket>> = rocketsRepository.getRocketsStream().map { pagingData ->
        pagingData.map(mapper::map)
    }
}