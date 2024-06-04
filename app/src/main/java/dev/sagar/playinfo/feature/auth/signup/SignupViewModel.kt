package dev.sagar.playinfo.feature.auth.signup

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

private const val PASSWORD_MIN_LENGTH = 6

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SignupViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private var isNameValidationActive = false
  private var isPasswordValidationActive = false
  private var isEmailValidationActive = false

  var signupState: SignupState by savedStateHandle.saveable { mutableStateOf(value = SignupState()) }
    private set

  fun onEvent(event: SignupEvent) {
    when (event) {
      is SignupEvent.EmailChanged -> {
        signupState = signupState.copy(
          email = event.email
        )
        isEmailValid()
      }

      is SignupEvent.PasswordChanged -> {
        signupState = signupState.copy(
          password = event.password
        )
        isPasswordValid()
      }

      is SignupEvent.NameChanged -> {
        signupState = signupState.copy(
          name = event.name
        )
        isNameValid()
      }

      SignupEvent.EmailIMEAction -> {
        isEmailValidationActive = true
        isEmailValid()
      }

      SignupEvent.NameIMEAction -> {
        isNameValidationActive = true
        isNameValid()
      }

      SignupEvent.PasswordIMEAction -> {
        isPasswordValidationActive = true
        isPasswordValid()
      }

      SignupEvent.Submit -> {
        // TODO: Handle submit
      }
    }

  }

  private fun isNameValid() {
    signupState = if (isNameValidationActive) {
      if (signupState.name.isNotBlank()) {
        signupState.copy(
          nameError = null
        )
      } else {
        signupState.copy(
          nameError = "Name is required"
        )
      }
    } else {
      signupState.copy(
        nameError = null
      )
    }
  }


  private fun isEmailValid() {
    signupState = if (isEmailValidationActive) {
      if (signupState.email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(signupState.email)
          .matches()
      ) {
        signupState.copy(
          emailError = null
        )
      } else {
        signupState.copy(
          emailError = "Invalid email"
        )
      }
    } else {
      signupState.copy(
        emailError = null
      )
    }
  }

  private fun isPasswordValid() {
    signupState = if (isPasswordValidationActive) {
      if (signupState.password.isNotBlank() && signupState.password.length >= PASSWORD_MIN_LENGTH) {
        signupState.copy(
          passwordError = null
        )
      } else {
        signupState.copy(
          passwordError = "Password must be at least $PASSWORD_MIN_LENGTH characters"
        )
      }
    } else {
      signupState.copy(
        passwordError = null
      )
    }
  }

}

