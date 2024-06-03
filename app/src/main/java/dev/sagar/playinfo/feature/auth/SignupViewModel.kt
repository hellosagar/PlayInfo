package dev.sagar.playinfo.feature.auth

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val PASSWORD_MIN_LENGTH = 6

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SignupViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  var name: String by savedStateHandle.saveable {
    mutableStateOf("")
  }

  var email: String by savedStateHandle.saveable {
    mutableStateOf("")
  }

  var password: String by savedStateHandle.saveable() {
    mutableStateOf("")
  }

  fun onNameChange(newValue: String) {
    name = newValue
  }

  fun onEmailChange(newValue: String) {
    email = newValue
  }

  fun onPasswordChange(newValue: String) {
    password = newValue
  }

  fun isNameValid(name: String): Boolean {
    return name.isNotBlank()
  }

  fun isEmailValid(email: String): Boolean {
    return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  fun isPasswordValid(password: String): Boolean {
    return password.isNotBlank() && password.length >= PASSWORD_MIN_LENGTH
  }

}
