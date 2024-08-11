package dev.sagar.playinfo.domain

data class GameItem(
    val id: Int,
    val name: String,
    val imageUrl: Images,
    val rating: Double,
    val released: String,
    val gamePlatforms: List<GamePlatform>,
    val tags: List<String>,
)

data class Images(
    val backgroundImage: String,
    val screenshots: List<String>,
)

enum class GamePlatform(val platform: String) {
    PC("PC"),
    PLAYSTATION("PlayStation"),
    XBOX("Xbox"),
    NINTENDO("Nintendo"),
    ANDROID("Android"),
    IOS("iOS"),
    LINUX("Linux"),
    MAC("Apple Macintosh"),
    WEB("Web"),
    UNKNOWN("Unknown");

    companion object {
        fun create(platform: String): GamePlatform {
            return when (platform) {
                "PC" -> PC
                "PlayStation" -> PLAYSTATION
                "Xbox" -> XBOX
                "Nintendo" -> NINTENDO
                "Android" -> ANDROID
                "iOS" -> IOS
                "Linux" -> LINUX
                "Apple Macintosh" -> MAC
                "Web" -> WEB
                else -> UNKNOWN
            }
        }
    }
}
