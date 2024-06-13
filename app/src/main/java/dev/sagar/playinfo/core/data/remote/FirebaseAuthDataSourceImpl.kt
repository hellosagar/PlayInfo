package dev.sagar.playinfo.core.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dev.sagar.playinfo.core.errorhandling.SafeFirebaseCall
import dev.sagar.playinfo.domain.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
  private val firebaseAuth: FirebaseAuth,
  private val safeFirebaseCall: SafeFirebaseCall,
) : FirebaseAuthDataSource {
  override suspend fun isUserLoggedIn(): Result<Unit> {
    firebaseAuth.currentUser?.let {
      return Result.Success(Unit)
    } ?: run {
      return Result.Error(Exception("User not found"))
    }
  }

  override suspend fun signup(name: String, email: String, password: String): Result<AuthResult> {
    return safeFirebaseCall.invoke {
      firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }
  }

  override suspend fun login(email: String, password: String): Result<AuthResult> {
    return safeFirebaseCall.invoke {
      firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }
  }

  override suspend fun updateUsername(name: String): Result<Unit> {
    return safeFirebaseCall.invoke {
      val user = firebaseAuth.currentUser
      val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
      if (user == null) {
        Result.Error(Exception("User not found"))
      } else {
        user.updateProfile(profileUpdates).await()
      }
    }
  }
}
