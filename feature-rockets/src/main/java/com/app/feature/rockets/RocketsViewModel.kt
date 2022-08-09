package com.app.feature.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.data.repository.RocketsRepository
import com.app.core.model.Rocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    rocketsRepository: RocketsRepository,
) : ViewModel() {

    val rockets: Flow<PagingData<Rocket>> =
        rocketsRepository.getRocketsStream().cachedIn(viewModelScope)
}