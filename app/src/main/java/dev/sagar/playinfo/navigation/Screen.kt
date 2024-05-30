package dev.sagar.playinfo.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
  @Serializable
  data object Home : Screen()

  @Serializable
  data class Detail(val id: String) : Screen()

}
