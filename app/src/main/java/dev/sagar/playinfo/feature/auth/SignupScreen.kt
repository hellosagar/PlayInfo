package dev.sagar.playinfo.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.sagar.playinfo.ui.theme.PlayInfoTheme

@Composable
internal fun SignupRoute(
  onSignupClick: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: SignupViewModel = hiltViewModel(),
) {

  SignupScreen(
    onClick = onSignupClick,
    modifier = modifier,
    nameValue = viewModel.name,
    emailValue = viewModel.email,
    passwordValue = viewModel.password,
    onNameChange = { name ->
      viewModel.onNameChange(name)
    },
    onEmailChange = { email ->
      viewModel.onEmailChange(email)
    },
    onPasswordChange = { password ->
      viewModel.onPasswordChange(password)
    },
  )
}

@Composable
private fun SignupScreen(
  onClick: () -> Unit,
  nameValue: String,
  emailValue: String,
  passwordValue: String,
  onNameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {

  Column(
    modifier = modifier
      .fillMaxSize(),
    verticalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      TextField(
        value = nameValue,
        onValueChange = { newValue ->
          onNameChange(newValue)
        },
        label = { Text("Name") },
      )

      Spacer(modifier = Modifier.height(16.dp))

      TextField(
        value = emailValue,
        onValueChange = { newValue ->
          onEmailChange(newValue)
        },
        label = { Text("Email") },
      )

      Spacer(modifier = Modifier.height(16.dp))

      TextField(
        value = passwordValue,
        onValueChange = { newValue ->
          onPasswordChange(newValue)
        },
        label = { Text("Password") },
      )
    }

    Button(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 16.dp),
      onClick = onClick,
    ) {
      Text("Sign Up")
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
