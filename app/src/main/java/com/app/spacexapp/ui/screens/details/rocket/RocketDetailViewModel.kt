package com.app.spacexapp.ui.screens.details.rocket

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.core.data.repository.RocketsRepository
import com.app.core.model.RocketDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val rocketsRepository: RocketsRepository,
) : ViewModel() {

    private val rocketId: String = checkNotNull(savedStateHandle["rocketId"])

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
                        rocketDetail = it,
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
