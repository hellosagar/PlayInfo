package dev.sagar.playinfo.feature.auth.signup

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

private const val SIGNUP_PASSWORD_VALIDATION_ACTIVE_KEY = "loginPasswordValidationActive"
private const val SIGNUP_EMAIL_VALIDATION_ACTIVE_KEY = "loginEmailValidationActive"
private const val SIGNUP_NAME_VALIDATION_ACTIVE_KEY = "loginNameValidationActive"

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SignupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val userAuthInputValidator: UserAuthInputValidator,
) : ViewModel() {

    private var isPasswordValidationActive: Boolean = false
        get() = savedStateHandle[SIGNUP_PASSWORD_VALIDATION_ACTIVE_KEY] ?: false
        set(value) {
            field = value
            savedStateHandle[SIGNUP_PASSWORD_VALIDATION_ACTIVE_KEY] = value
        }
    private var isNameValidationActive = false
        get() = savedStateHandle[SIGNUP_NAME_VALIDATION_ACTIVE_KEY] ?: false
        set(value) {
            field = value
            savedStateHandle[SIGNUP_NAME_VALIDATION_ACTIVE_KEY] = value
        }
    private var isEmailValidationActive = false
        get() = savedStateHandle[SIGNUP_EMAIL_VALIDATION_ACTIVE_KEY] ?: false
        set(value) {
            field = value
            savedStateHandle[SIGNUP_EMAIL_VALIDATION_ACTIVE_KEY] = value
        }

    private val _viewState = MutableStateFlow(SignupViewState())
    val viewState: StateFlow<SignupViewState> = _viewState

    private val _action = MutableSharedFlow<SignupAction>()
    val action: SharedFlow<SignupAction> = _action

    var signupInputState: SignupInputState by savedStateHandle.saveable { mutableStateOf(value = SignupInputState()) }
        private set

    init {
        validateInputs()
    }

    fun onEvent(event: SignupInputActionEvent) {
        when (event) {
            is SignupInputActionEvent.EmailChanged -> onEmailChanged(event.email)
            is SignupInputActionEvent.PasswordChanged -> onPasswordChanged(event.password)
            is SignupInputActionEvent.NameChanged -> onNameChanged(event.name)
            SignupInputActionEvent.EmailIMEInputAction -> onEmailIMEActionClick()
            SignupInputActionEvent.NameIMEInputAction -> onNameIMEInputActionClick()
            SignupInputActionEvent.PasswordIMEInputAction -> onPasswordIMEActionClick()
            SignupInputActionEvent.Submit -> submitSignup()
        }
    }

    private fun onEmailChanged(email: String) {
        signupInputState = signupInputState.copy(
            email = email,
            emailError = if (isEmailValidationActive) userAuthInputValidator.emailValid(email) else null
        )
        validateInputs()
    }

    private fun onPasswordChanged(password: String) {
        signupInputState = signupInputState.copy(
            password = password,
            passwordError = if (isPasswordValidationActive) userAuthInputValidator.passwordValid(password) else null
        )
        validateInputs()
    }

    private fun onNameChanged(name: String) {
        signupInputState = signupInputState.copy(
            name = name,
            nameError = if (isNameValidationActive) userAuthInputValidator.nameValid(name) else null
        )
        validateInputs()
    }

    private fun onNameIMEInputActionClick() {
        isNameValidationActive = true
        signupInputState = signupInputState.copy(
            nameError = userAuthInputValidator.nameValid(signupInputState.name)
        )
    }

    private fun onEmailIMEActionClick() {
        isEmailValidationActive = true
        signupInputState = signupInputState.copy(
            emailError = userAuthInputValidator.emailValid(signupInputState.email)
        )
    }

    private fun onPasswordIMEActionClick() {
        isPasswordValidationActive = true
        signupInputState = signupInputState.copy(
            passwordError = userAuthInputValidator.passwordValid(signupInputState.password)
        )
    }

    private fun validateInputs() {
        _viewState.value = _viewState.value.copy(
            submitButtonEnabled = userAuthInputValidator.isCreateAccountInputValid(
                email = signupInputState.email,
                password = signupInputState.password,
                name = signupInputState.name
            )
        )
    }

    private fun submitSignup() {
        viewModelScope.launch {
            val result = authRepository.signUp(
                name = signupInputState.name,
                email = signupInputState.email,
                password = signupInputState.password
            )
            handleSignupResult(result)
        }
    }

    private suspend fun handleSignupResult(result: Result<Unit>) {
        when (result) {
            is Result.Success -> {
                _action.emit(SignupAction.NavigationToHome)
                _action.emit(SignupAction.ShowSnackBar("Signup successful"))
            }

            is Result.Error -> {
                _action.emit(SignupAction.ShowSnackBar(result.error.message ?: "Signup failed"))
            }
        }
    }
}

data class SignupViewState(
    val isLoading: Boolean = false,
    val submitButtonEnabled: Boolean = false,
)

sealed class SignupAction {
    data object NavigationToHome : SignupAction()
    data class ShowSnackBar(val message: String) : SignupAction()
}

data class SignupInputState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)

sealed class SignupInputActionEvent {
    data class EmailChanged(val email: String) : SignupInputActionEvent()
    data object EmailIMEInputAction : SignupInputActionEvent()
    data class PasswordChanged(val password: String) : SignupInputActionEvent()
    data object PasswordIMEInputAction : SignupInputActionEvent()
    data class NameChanged(val name: String) : SignupInputActionEvent()
    data object NameIMEInputAction : SignupInputActionEvent()
    data object Submit : SignupInputActionEvent()
}
