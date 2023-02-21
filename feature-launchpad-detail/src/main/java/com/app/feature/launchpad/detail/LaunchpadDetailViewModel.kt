package com.app.feature.launchpad.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.core.data.repository.LaunchpadsRepository
import com.app.core.model.LaunchpadDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchpadDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val launchpadRepository: LaunchpadsRepository,
) : ViewModel() {

    private val launchpadId: String = checkNotNull(savedStateHandle["launchpadId"])

    val state = MutableStateFlow(LaunchpadDetailViewState())

    init {
        getLaunchpadDetails()
    }

    fun getLaunchpadDetails() {
        state.value = state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            runCatching { launchpadRepository.getLaunchpadById(launchpadId) }
                .onSuccess {
                    state.value = state.value.copy(
                        launchpadDetail = it,
                        loading = false,
                        error = null,
                    )
                }
                .onFailure { state.value = state.value.copy(loading = false, error = it) }

        }
    }

    data class LaunchpadDetailViewState(
        val launchpadDetail: LaunchpadDetail? = null,
        val loading: Boolean = false,
        val error: Throwable? = null,
    )
}
