package dev.sagar.playinfo.feature.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  firebaseAuth: FirebaseAuth,
): ViewModel() {
  init {
    println("-_- firebaseAuth.currentUser?.email: ${firebaseAuth.currentUser?.email}")
    println("-_- firebaseAuth.currentUser?.displayName: ${firebaseAuth.currentUser?.displayName}")

  }
}
