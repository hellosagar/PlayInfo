/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sagar.playinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sagar.playinfo.data.repository.AuthRepository
import dev.sagar.playinfo.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authRepository: AuthRepository,
) : ViewModel() {

    private val _isUserSignedIn: MutableStateFlow<MainActivityUiState> = MutableStateFlow(MainActivityUiState.Loading)
    val isUserSignedIn: StateFlow<MainActivityUiState> = _isUserSignedIn

    init {
        viewModelScope.launch {
            when (authRepository.isUserLoggedIn()) {
                is Result.Success -> _isUserSignedIn.emit(MainActivityUiState.Success(true))
                is Result.Error -> _isUserSignedIn.emit(MainActivityUiState.Success(false))
            }
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val isUserLoggedIn: Boolean) : MainActivityUiState
}
