package dev.sagar.playinfo.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.sagar.playinfo.ui.theme.PlayInfoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
internal fun LoginRoute(
  onLoginClick: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: LoginViewModel = hiltViewModel(),
  onShowSnackbar: suspend (message: String, actionText: String?) -> Boolean,
) {
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  LaunchedEffect(onLoginClick) {
    viewModel.action.collectLatest { actions ->
      when (actions) {
        LoginAction.NavigationToHome -> {
          onLoginClick()
        }

        is LoginAction.ShowSnackBar -> {
          onShowSnackbar.invoke(
            actions.message,
            null
          )
        }
      }
    }
  }

  LoginScreen(
    viewState = viewState,
    onSubmitClick = {
      viewModel.onEvent(
        LoginInputActionEvent.Submit
      )
    },
    modifier = modifier,
    emailValue = viewModel.loginInputState.email,
    emailErrorValue = viewModel.loginInputState.emailError,
    passwordValue = viewModel.loginInputState.password,
    passwordErrorValue = viewModel.loginInputState.passwordError,
    onEmailChange = { email ->
      viewModel.onEvent(LoginInputActionEvent.EmailChanged(email))
    },
    onPasswordChange = { password ->
      viewModel.onEvent(LoginInputActionEvent.PasswordChanged(password))
    },
    onEmailNextClick = {
      viewModel.onEvent(LoginInputActionEvent.EmailIMEInputAction)
    },
    onPasswordNextClick = {
      viewModel.onEvent(LoginInputActionEvent.PasswordIMEInputAction)
    },
  )
}

@Composable
internal fun LoginScreen(
  onSubmitClick: () -> Unit,
  emailValue: String,
  emailErrorValue: String?,
  passwordValue: String,
  passwordErrorValue: String?,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onEmailNextClick: () -> Unit,
  onPasswordNextClick: () -> Unit,
  viewState: LoginViewState,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.SpaceBetween,
  ) {
    val localFocusManager = LocalFocusManager.current

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      TextField(
        value = emailValue,
        onValueChange = { newValue ->
          onEmailChange(newValue)
        },
        label = { Text("Email") },
        placeholder = { Text("xyz@gmail.com") },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
          onNext = {
            onEmailNextClick.invoke()
            localFocusManager.moveFocus(FocusDirection.Down)
          }),
        leadingIcon = {
          Icon(Icons.Filled.Email, contentDescription = "Email")
        },
        supportingText = {
          Text(
            text = emailErrorValue ?: "",
            color = Color.Red,
          )
        },
        isError = emailErrorValue != null,
      )

      Spacer(modifier = Modifier.height(9.dp))

      var passwordVisible by rememberSaveable { mutableStateOf(false) }
      TextField(
        value = passwordValue,
        onValueChange = { newValue ->
          onPasswordChange(newValue)
        },
        label = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text("Enter your password") },
        keyboardOptions =
        KeyboardOptions(
          keyboardType = KeyboardType.Password,
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = {
            onPasswordNextClick.invoke()
            localFocusManager.clearFocus()
          }),
        trailingIcon = {
          val image = if (passwordVisible)
            Icons.Filled.Visibility
          else Icons.Filled.VisibilityOff

          val description = if (passwordVisible) "Hide password" else "Show password"

          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(imageVector = image, contentDescription = description)
          }
        },
        leadingIcon = {
          Icon(Icons.Filled.Password, contentDescription = "Password")
        },
        supportingText = {
          Text(
            text = passwordErrorValue ?: "",
            color = Color.Red,
          )
        },
        isError = passwordErrorValue != null,
      )
    }

    Button(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 16.dp),
      onClick = onSubmitClick,
      enabled = viewState.submitButtonEnabled,
    ) {
      Text("Login In")
    }
  }

}


@Preview(showBackground = true)
@Composable
internal fun AuthPreview() {
  PlayInfoTheme {
//    SignupScreen({})
  }
}
