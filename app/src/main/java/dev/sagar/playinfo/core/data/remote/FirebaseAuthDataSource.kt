package dev.sagar.playinfo.core.data.remote

import dev.sagar.playinfo.domain.Result

interface FirebaseAuthDataSource {
  suspend fun signup(name: String, email: String, password: String): Result<Unit>
}
