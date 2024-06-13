package dev.sagar.playinfo.core.data.repository

import dev.sagar.playinfo.domain.Result

interface AuthRepository {
  suspend fun isUserLoggedIn(): Result<Unit>
  suspend fun signUp(name: String, email: String, password: String): Result<Unit>
  suspend fun login(email: String, password: String): Result<Unit>

}
