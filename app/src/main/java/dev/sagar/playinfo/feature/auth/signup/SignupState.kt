package dev.sagar.playinfo.feature.auth.signup

data class SignupState(
  val name: String = "",
  val nameError: String? = null,
  val email: String = "",
  val emailError: String? = null,
  val password: String = "",
  val passwordError: String? = null,
)
