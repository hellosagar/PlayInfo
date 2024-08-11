package dev.sagar.playinfo.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sagar.playinfo.domain.GameItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesPagerUseCase: GetGamesPagerUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)
            _viewState.value = _viewState.value.copy(
                isLoading = false,
                games = getGamesPagerUseCase.execute().cachedIn(viewModelScope)
            )
        }
    }
}

data class HomeViewState(
    val games: Flow<PagingData<GameItem>>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
