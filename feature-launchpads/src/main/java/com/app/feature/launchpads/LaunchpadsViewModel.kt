package com.app.feature.launchpads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.data.repository.LaunchpadsRepository
import com.app.core.model.Launchpad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LaunchpadsViewModel @Inject constructor(
    launchpadsRepository: LaunchpadsRepository,
) : ViewModel() {

    val launchpads: Flow<PagingData<Launchpad>> =
        launchpadsRepository.getLaunchpadsStream().cachedIn(viewModelScope)
}