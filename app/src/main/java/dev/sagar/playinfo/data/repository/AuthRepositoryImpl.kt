package dev.sagar.playinfo.data.repository

import dev.sagar.playinfo.data.remote.FirebaseAuthDataSource
import dev.sagar.playinfo.domain.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
) : AuthRepository {
    override suspend fun isUserLoggedIn(): Result<Unit> =
        firebaseAuthDataSource.isUserLoggedIn()

    override suspend fun signUp(name: String, email: String, password: String): Result<Unit> {
        return when (val result = firebaseAuthDataSource.signup(name, email, password)) {
            is Result.Success -> {
                firebaseAuthDataSource.updateUsername(name).let {
                    return when (it) {
                        is Result.Success -> Result.Success(Unit)
                        is Result.Error -> Result.Error(it.error)
                    }
                }
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        firebaseAuthDataSource.login(email, password).let {
            return when (it) {
                is Result.Success -> Result.Success(Unit)
                is Result.Error -> Result.Error(it.error)
            }
        }
    }
}
