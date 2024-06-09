package dev.sagar.playinfo.core.data.repository

import dev.sagar.playinfo.core.data.remote.FirebaseAuthDataSource
import dev.sagar.playinfo.domain.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val firebaseAuthDataSource: FirebaseAuthDataSource,
) : AuthRepository {
  override suspend fun signup(name: String, email: String, password: String): Result<Unit> {
    return firebaseAuthDataSource.signup(name, email, password)
  }
}
