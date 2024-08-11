package dev.sagar.playinfo.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameDetailResponse(
    @Json(name = "achievements_count")
    val achievementsCount: Int?,
    @Json(name = "added")
    val added: Int?,
    @Json(name = "added_by_status")
    val addedByStatus: AddedByStatus?,
    @Json(name = "additions_count")
    val additionsCount: Int?,
    @Json(name = "alternative_names")
    val alternativeNames: List<Any?>?,
    @Json(name = "background_image")
    val backgroundImage: String?,
    @Json(name = "background_image_additional")
    val backgroundImageAdditional: String?,
    @Json(name = "clip")
    val clip: Any?,
    @Json(name = "creators_count")
    val creatorsCount: Int?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "description_raw")
    val descriptionRaw: String?,
    @Json(name = "developers")
    val developers: List<Developer?>?,
    @Json(name = "dominant_color")
    val dominantColor: String?,
    @Json(name = "esrb_rating")
    val esrbRating: EsrbRating?,
    @Json(name = "game_series_count")
    val gameSeriesCount: Int?,
    @Json(name = "genres")
    val genres: List<Genre?>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "metacritic")
    val metacritic: Int?,
    @Json(name = "metacritic_platforms")
    val metacriticPlatforms: List<MetacriticPlatform?>?,
    @Json(name = "metacritic_url")
    val metacriticUrl: String?,
    @Json(name = "movies_count")
    val moviesCount: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "name_original")
    val nameOriginal: String?,
    @Json(name = "parent_achievements_count")
    val parentAchievementsCount: Int?,
    @Json(name = "parent_platforms")
    val parentPlatforms: List<ParentPlatform?>?,
    @Json(name = "parents_count")
    val parentsCount: Int?,
    @Json(name = "platforms")
    val platforms: List<Platform?>?,
    @Json(name = "playtime")
    val playtime: Int?,
    @Json(name = "publishers")
    val publishers: List<Publisher?>?,
    @Json(name = "rating")
    val rating: Double?,
    @Json(name = "rating_top")
    val ratingTop: Int?,
    @Json(name = "ratings")
    val ratings: List<Rating?>?,
    @Json(name = "ratings_count")
    val ratingsCount: Int?,
    @Json(name = "reactions")
    val reactions: Reactions?,
    @Json(name = "reddit_count")
    val redditCount: Int?,
    @Json(name = "reddit_description")
    val redditDescription: String?,
    @Json(name = "reddit_logo")
    val redditLogo: String?,
    @Json(name = "reddit_name")
    val redditName: String?,
    @Json(name = "reddit_url")
    val redditUrl: String?,
    @Json(name = "released")
    val released: String?,
    @Json(name = "reviews_count")
    val reviewsCount: Int?,
    @Json(name = "reviews_text_count")
    val reviewsTextCount: Int?,
    @Json(name = "saturated_color")
    val saturatedColor: String?,
    @Json(name = "screenshots_count")
    val screenshotsCount: Int?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "stores")
    val stores: List<Store?>?,
    @Json(name = "suggestions_count")
    val suggestionsCount: Int?,
    @Json(name = "tags")
    val tags: List<Tag?>?,
    @Json(name = "tba")
    val tba: Boolean?,
    @Json(name = "twitch_count")
    val twitchCount: Int?,
    @Json(name = "updated")
    val updated: String?,
    @Json(name = "user_game")
    val userGame: Any?,
    @Json(name = "website")
    val website: String?,
    @Json(name = "youtube_count")
    val youtubeCount: Int?
) {
    @JsonClass(generateAdapter = true)
    data class AddedByStatus(
        @Json(name = "beaten")
        val beaten: Int?,
        @Json(name = "dropped")
        val dropped: Int?,
        @Json(name = "owned")
        val owned: Int?,
        @Json(name = "playing")
        val playing: Int?,
        @Json(name = "toplay")
        val toplay: Int?,
        @Json(name = "yet")
        val yet: Int?
    )

    @JsonClass(generateAdapter = true)
    data class Developer(
        @Json(name = "games_count")
        val gamesCount: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image_background")
        val imageBackground: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?
    )

    @JsonClass(generateAdapter = true)
    data class EsrbRating(
        @Json(name = "id")
        val id: Int?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "games_count")
        val gamesCount: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image_background")
        val imageBackground: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?
    )

    @JsonClass(generateAdapter = true)
    data class MetacriticPlatform(
        @Json(name = "metascore")
        val metascore: Int?,
        @Json(name = "platform")
        val platform: Platform?,
        @Json(name = "url")
        val url: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Platform(
            @Json(name = "name")
            val name: String?,
            @Json(name = "platform")
            val platform: Int?,
            @Json(name = "slug")
            val slug: String?
        )
    }

    @JsonClass(generateAdapter = true)
    data class ParentPlatform(
        @Json(name = "platform")
        val platform: Platform?
    ) {
        @JsonClass(generateAdapter = true)
        data class Platform(
            @Json(name = "id")
            val id: Int?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "slug")
            val slug: String?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Platform(
        @Json(name = "platform")
        val platform: Platform?,
        @Json(name = "released_at")
        val releasedAt: String?,
        @Json(name = "requirements")
        val requirements: Requirements?
    ) {
        @JsonClass(generateAdapter = true)
        data class Platform(
            @Json(name = "games_count")
            val gamesCount: Int?,
            @Json(name = "id")
            val id: Int?,
            @Json(name = "image")
            val image: Any?,
            @Json(name = "image_background")
            val imageBackground: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "slug")
            val slug: String?,
            @Json(name = "year_end")
            val yearEnd: Any?,
            @Json(name = "year_start")
            val yearStart: Int?
        )

        @JsonClass(generateAdapter = true)
        class Requirements
    }

    @JsonClass(generateAdapter = true)
    data class Publisher(
        @Json(name = "games_count")
        val gamesCount: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image_background")
        val imageBackground: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?
    )

    @JsonClass(generateAdapter = true)
    data class Rating(
        @Json(name = "count")
        val count: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "percent")
        val percent: Double?,
        @Json(name = "title")
        val title: String?
    )

    @JsonClass(generateAdapter = true)
    data class Reactions(
        @Json(name = "1")
        val x1: Int?,
        @Json(name = "10")
        val x10: Int?,
        @Json(name = "11")
        val x11: Int?,
        @Json(name = "12")
        val x12: Int?,
        @Json(name = "14")
        val x14: Int?,
        @Json(name = "15")
        val x15: Int?,
        @Json(name = "16")
        val x16: Int?,
        @Json(name = "2")
        val x2: Int?,
        @Json(name = "21")
        val x21: Int?,
        @Json(name = "3")
        val x3: Int?,
        @Json(name = "4")
        val x4: Int?,
        @Json(name = "5")
        val x5: Int?,
        @Json(name = "6")
        val x6: Int?,
        @Json(name = "7")
        val x7: Int?
    )

    @JsonClass(generateAdapter = true)
    data class Store(
        @Json(name = "id")
        val id: Int?,
        @Json(name = "store")
        val store: Store?,
        @Json(name = "url")
        val url: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Store(
            @Json(name = "domain")
            val domain: String?,
            @Json(name = "games_count")
            val gamesCount: Int?,
            @Json(name = "id")
            val id: Int?,
            @Json(name = "image_background")
            val imageBackground: String?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "slug")
            val slug: String?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Tag(
        @Json(name = "games_count")
        val gamesCount: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image_background")
        val imageBackground: String?,
        @Json(name = "language")
        val language: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?
    )
}
