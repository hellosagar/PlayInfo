package dev.sagar.playinfo.feature.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sagar.playinfo.core.utils.UserAuthInputValidator
import dev.sagar.playinfo.data.repository.AuthRepository
import dev.sagar.playinfo.domain.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LOGIN_PASSWORD_VALIDATION_ACTIVE_KEY = "loginPasswordValidationActive"
private const val LOGIN_EMAIL_VALIDATION_ACTIVE_KEY = "loginEmailValidationActive"

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val userAuthInputValidator: UserAuthInputValidator,
) : ViewModel() {

    private var isPasswordValidationActive: Boolean = false
        get() = savedStateHandle[LOGIN_PASSWORD_VALIDATION_ACTIVE_KEY] ?: false
        set(value) {
            field = value
            savedStateHandle[LOGIN_PASSWORD_VALIDATION_ACTIVE_KEY] = value
        }

    private var isEmailValidationActive: Boolean = false
        get() = savedStateHandle[LOGIN_EMAIL_VALIDATION_ACTIVE_KEY] ?: false
        set(value) {
            field = value
            savedStateHandle[LOGIN_EMAIL_VALIDATION_ACTIVE_KEY] = value
        }

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> = _viewState

    private val _action = MutableSharedFlow<LoginAction>()
    val action: SharedFlow<LoginAction> = _action

    var loginInputState: LoginInputState by savedStateHandle.saveable { mutableStateOf(value = LoginInputState()) }
        private set

    init {
        validateInputs()
    }

    fun onEvent(event: LoginInputActionEvent) {
        when (event) {
            is LoginInputActionEvent.EmailChanged -> onEmailChanged(event.email)
            is LoginInputActionEvent.PasswordChanged -> onPasswordChanged(event.password)
            LoginInputActionEvent.EmailIMEInputAction -> onEmailIMEActionClick()
            LoginInputActionEvent.PasswordIMEInputAction -> onPasswordIMEActionClick()
            LoginInputActionEvent.Submit -> submitSignup()
        }
    }

    private fun onEmailChanged(email: String) {
        loginInputState = loginInputState.copy(
            email = email,
            emailError = if (isEmailValidationActive) userAuthInputValidator.emailValid(email) else null
        )
        validateInputs()
    }

    private fun onPasswordChanged(password: String) {
        loginInputState = loginInputState.copy(
            password = password,
            passwordError = if (isPasswordValidationActive) userAuthInputValidator.passwordValid(password) else null
        )
        validateInputs()
    }

    private fun onEmailIMEActionClick() {
        isEmailValidationActive = true
        loginInputState = loginInputState.copy(
            emailError = userAuthInputValidator.emailValid(loginInputState.email)
        )
    }

    private fun onPasswordIMEActionClick() {
        isPasswordValidationActive = true
        loginInputState = loginInputState.copy(
            passwordError = userAuthInputValidator.passwordValid(loginInputState.password)
        )
    }

    private fun validateInputs() {
        _viewState.value = _viewState.value.copy(
            submitButtonEnabled = userAuthInputValidator.isLoginInputValid(
                email = loginInputState.email,
                password = loginInputState.password,
            )
        )
    }

    private fun submitSignup() {
        viewModelScope.launch {
            val result = authRepository.login(
                email = loginInputState.email,
                password = loginInputState.password
            )
            handleSignupResult(result)
        }
    }

    private suspend fun handleSignupResult(result: Result<Unit>) {
        when (result) {
            is Result.Success -> {
                _action.emit(LoginAction.NavigationToHome)
                _action.emit(LoginAction.ShowSnackBar("Login successful"))
            }

            is Result.Error -> {
                _action.emit(LoginAction.ShowSnackBar(result.error.message ?: "Login failed"))
            }
        }
    }
}

data class LoginViewState(
    val isLoading: Boolean = false,
    val submitButtonEnabled: Boolean = false,
)

sealed class LoginAction {
    data object NavigationToHome : LoginAction()
    data class ShowSnackBar(val message: String) : LoginAction()
}

data class LoginInputState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)

sealed class LoginInputActionEvent {
    data class EmailChanged(val email: String) : LoginInputActionEvent()
    data object EmailIMEInputAction : LoginInputActionEvent()
    data class PasswordChanged(val password: String) : LoginInputActionEvent()
    data object PasswordIMEInputAction : LoginInputActionEvent()
    data object Submit : LoginInputActionEvent()
}
