package dev.sagar.playinfo.feature.auth.signup

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
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
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
internal fun SignupRoute(
    onSignupClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    onShowSnackbar: suspend (message: String, actionText: String?) -> Boolean,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(onSignupClick) {
        viewModel.action.collectLatest { actions ->
            when (actions) {
                SignupAction.NavigationToHome -> {
                    onSignupClick()
                }

                is SignupAction.ShowSnackBar -> {
                    onShowSnackbar.invoke(
                        actions.message,
                        null
                    )
                }
            }
        }
    }

    SignupScreen(
        viewState = viewState,
        onSubmitClick = {
            viewModel.onEvent(
                SignupInputActionEvent.Submit
            )
        },
        modifier = modifier,
        nameValue = viewModel.signupInputState.name,
        nameErrorValue = viewModel.signupInputState.nameError,
        emailValue = viewModel.signupInputState.email,
        emailErrorValue = viewModel.signupInputState.emailError,
        passwordValue = viewModel.signupInputState.password,
        passwordErrorValue = viewModel.signupInputState.passwordError,
        onNameChange = { name ->
            viewModel.onEvent(SignupInputActionEvent.NameChanged(name))
        },
        onEmailChange = { email ->
            viewModel.onEvent(SignupInputActionEvent.EmailChanged(email))
        },
        onPasswordChange = { password ->
            viewModel.onEvent(SignupInputActionEvent.PasswordChanged(password))
        },
        onNameNextClick = {
            viewModel.onEvent(SignupInputActionEvent.NameIMEInputAction)
        },
        onEmailNextClick = {
            viewModel.onEvent(SignupInputActionEvent.EmailIMEInputAction)
        },
        onPasswordNextClick = {
            viewModel.onEvent(SignupInputActionEvent.PasswordIMEInputAction)
        },
    )
}

@Composable
internal fun SignupScreen(
    onSubmitClick: () -> Unit,
    nameValue: String,
    nameErrorValue: String?,
    emailValue: String,
    emailErrorValue: String?,
    passwordValue: String,
    passwordErrorValue: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNameNextClick: () -> Unit,
    onEmailNextClick: () -> Unit,
    onPasswordNextClick: () -> Unit,
    viewState: SignupViewState,
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
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            onNameNextClick.invoke()
                        }
                    }
                    .testTag("signup_name_text_field"),
                value = nameValue,
                onValueChange = { newValue ->
                    onNameChange(newValue)
                },
                label = { Text("Username") },
                placeholder = { Text("Enter your name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                leadingIcon = {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Username")
                },
                supportingText = {
                    Text(
                        text = nameErrorValue ?: "",
                        color = Color.Red,
                    )
                },
                isError = nameErrorValue != null,
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            onEmailNextClick.invoke()
                        }
                    }
                    .testTag("signup_email_text_field"),
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
                        localFocusManager.moveFocus(FocusDirection.Down)
                    }
                ),
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
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            onPasswordNextClick.invoke()
                        }
                    }
                    .testTag("signup_password_text_field"),
                value = passwordValue,
                onValueChange = { newValue ->
                    onPasswordChange(newValue)
                },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                placeholder = { Text("Enter your password") },
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus(force = true)
                    }
                ),
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

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
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .testTag("signup_submit_button"),
            onClick = onSubmitClick,
            enabled = viewState.submitButtonEnabled,
        ) {
            Text("Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AuthPreview() {
    PlayInfoTheme {
        SignupScreen(
            onPasswordNextClick = {},
            onEmailNextClick = {},
            onPasswordChange = {},
            passwordErrorValue = null,
            modifier = Modifier,
            nameValue = "Name",
            viewState = SignupViewState(isLoading = false, submitButtonEnabled = true),
            onNameChange = {},
            onNameNextClick = {},
            onEmailChange = {},
            emailValue = "Email",
            emailErrorValue = null,
            passwordValue = "Password",
            onSubmitClick = {},
            nameErrorValue = null
        )
    }
}
