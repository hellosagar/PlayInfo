package dev.sagar.playinfo.core.data.remote

import com.google.firebase.auth.FirebaseAuth
import dev.sagar.playinfo.core.errorhandling.SafeFirebaseCall
import dev.sagar.playinfo.domain.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
  private val firebaseAuth: FirebaseAuth,
  private val safeFirebaseCall: SafeFirebaseCall,
) : FirebaseAuthDataSource {
  override suspend fun signup(name: String, email: String, password: String): Result<Unit> {
    return safeFirebaseCall.invoke {
      firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

  }
}
