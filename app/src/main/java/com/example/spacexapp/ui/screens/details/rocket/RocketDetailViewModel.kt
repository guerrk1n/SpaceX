package com.example.spacexapp.ui.screens.details.rocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacexapp.api.SpaceXService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RocketDetailViewModel(
    private val spaceXService: SpaceXService,
    private val mapper: RocketDetailMapper,
    private val rocketId: String,
) : ViewModel() {

    val state = MutableStateFlow(RocketDetailViewState())

    init {
        getRocketDetails()
    }

    fun getRocketDetails() = viewModelScope.launch {
        state.value = state.value.copy(loading = true, error = null)
        state.value = try {
            val rocketDetailsResponse = spaceXService.getRocketDetail(rocketId)
            state.value.copy(
                rocketDetail = mapper.map(rocketDetailsResponse),
                loading = false,
                error = null,
            )
        } catch (exception: Exception) {
            state.value.copy(loading = false, error = exception)
        }
    }

    data class RocketDetailViewState(
        val rocketDetail: RocketDetail? = null,
        val loading: Boolean = false,
        val error: Throwable? = null,
    )
}
