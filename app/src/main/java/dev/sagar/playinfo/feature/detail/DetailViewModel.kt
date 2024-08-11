package dev.sagar.playinfo.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sagar.playinfo.data.repository.GameRepository
import dev.sagar.playinfo.domain.GameDetail
import dev.sagar.playinfo.domain.Result
import dev.sagar.playinfo.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.DetailViewModelFactory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val initData: Screen.Detail,
    private val gameRepository: GameRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(DetailViewState())
    val viewState: StateFlow<DetailViewState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)
            _viewState.value = _viewState.value.copy(
                isLoading = false,
                games = getGameDetail()
            )
        }
    }

    private suspend fun getGameDetail(): GameDetail? {
        return when (val gameDetail = gameRepository.getGameDetail(initData.id)) {
            is Result.Error -> {
                gameDetail.error
                null
            }

            is Result.Success -> {
                gameDetail.data
            }
        }
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(id: Screen.Detail): DetailViewModel
    }
}

data class DetailViewState(
    val games: GameDetail? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
