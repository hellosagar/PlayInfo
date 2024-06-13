package dev.sagar.playinfo.core.data.remote

import com.google.firebase.auth.AuthResult
import dev.sagar.playinfo.domain.Result

interface FirebaseAuthDataSource {
  suspend fun isUserLoggedIn(): Result<Unit>
  suspend fun signup(name: String, email: String, password: String): Result<AuthResult>
  suspend fun login(email: String, password: String): Result<AuthResult>
  suspend fun updateUsername(name: String): Result<Unit>
}
