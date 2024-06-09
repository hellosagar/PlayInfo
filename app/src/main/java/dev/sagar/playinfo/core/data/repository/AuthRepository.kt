package dev.sagar.playinfo.core.data.repository

import dev.sagar.playinfo.domain.Result

interface AuthRepository {

  suspend fun signup(name: String, email: String, password: String): Result<Unit>

}
