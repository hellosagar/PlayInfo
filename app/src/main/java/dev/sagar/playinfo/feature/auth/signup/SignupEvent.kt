package dev.sagar.playinfo.feature.auth.signup

sealed class SignupEvent {
  data class EmailChanged(val email: String) : SignupEvent()
  data object EmailIMEAction : SignupEvent()
  data class PasswordChanged(val password: String) : SignupEvent()
  data object PasswordIMEAction : SignupEvent()
  data class NameChanged(val name: String) : SignupEvent()
  data object NameIMEAction : SignupEvent()
  data object Submit : SignupEvent()
}
