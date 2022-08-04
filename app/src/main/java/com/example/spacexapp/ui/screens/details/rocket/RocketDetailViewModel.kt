package com.example.spacexapp.ui.screens.details.rocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacexapp.data.repository.RocketsRepository
import com.example.spacexapp.model.local.mappers.RocketEntityToRocketDetailMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RocketDetailViewModel(
    private val rocketsRepository: RocketsRepository,
    private val mapper: RocketEntityToRocketDetailMapper,
    private val rocketId: String,
) : ViewModel() {

    val state = MutableStateFlow(RocketDetailViewState())

    init {
        getRocketDetails()
    }

    fun getRocketDetails() {
        state.value = state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            runCatching { rocketsRepository.getRocketById(rocketId) }
                .onSuccess {
                    state.value = state.value.copy(
                        rocketDetail = mapper.map(it),
                        loading = false,
                        error = null,
                    )
                }
                .onFailure { state.value = state.value.copy(loading = false, error = it) }

        }
    }

    data class RocketDetailViewState(
        val rocketDetail: RocketDetail? = null,
        val loading: Boolean = false,
        val error: Throwable? = null,
    )
}
