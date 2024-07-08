package dev.sagar.playinfo.core.data.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameListResponse(
  @Json(name = "count")
  val count: Int?,
  @Json(name = "description")
  val description: String?,
  @Json(name = "filters")
  val filters: FiltersResponse?,
  @Json(name = "next")
  val next: String?,
  @Json(name = "nofollow")
  val nofollow: Boolean?,
  @Json(name = "nofollow_collections")
  val nofollowCollections: List<String?>?,
  @Json(name = "noindex")
  val noindex: Boolean?,
  @Json(name = "results")
  val results: List<ResultResponse?>?,
  @Json(name = "seo_description")
  val seoDescription: String?,
  @Json(name = "seo_h1")
  val seoH1: String?,
  @Json(name = "seo_keywords")
  val seoKeywords: String?,
  @Json(name = "seo_title")
  val seoTitle: String?,
) {
  @JsonClass(generateAdapter = true)
  data class FiltersResponse(
    @Json(name = "years")
    val years: List<YearResponse?>?,
  ) {
    @JsonClass(generateAdapter = true)
    data class YearResponse(
      @Json(name = "count")
      val count: Int?,
      @Json(name = "decade")
      val decade: Int?,
      @Json(name = "filter")
      val filter: String?,
      @Json(name = "from")
      val from: Int?,
      @Json(name = "nofollow")
      val nofollow: Boolean?,
      @Json(name = "to")
      val to: Int?,
      @Json(name = "years")
      val years: List<YearResponse?>?,
    ) {
      @JsonClass(generateAdapter = true)
      data class YearResponse(
        @Json(name = "count")
        val count: Int?,
        @Json(name = "nofollow")
        val nofollow: Boolean?,
        @Json(name = "year")
        val year: Int?,
      )
    }
  }

  @JsonClass(generateAdapter = true)
  data class ResultResponse(
    @Json(name = "added")
    val added: Int?,
    @Json(name = "added_by_status")
    val addedByStatus: AddedByStatusResponse?,
    @Json(name = "background_image")
    val backgroundImage: String?,
    @Json(name = "dominant_color")
    val dominantColor: String?,
    @Json(name = "esrb_rating")
    val esrbRating: EsrbRatingResponse?,
    @Json(name = "genres")
    val genres: List<GenreResponse?>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "metacritic")
    val metacritic: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "parent_platforms")
    val parentPlatforms: List<ParentPlatformResponse?>?,
    @Json(name = "platforms")
    val platforms: List<PlatformResponse?>?,
    @Json(name = "playtime")
    val playtime: Int?,
    @Json(name = "rating")
    val rating: Double?,
    @Json(name = "rating_top")
    val ratingTop: Int?,
    @Json(name = "ratings")
    val ratings: List<RatingResponse?>?,
    @Json(name = "ratings_count")
    val ratingsCount: Int?,
    @Json(name = "released")
    val released: String?,
    @Json(name = "reviews_count")
    val reviewsCount: Int?,
    @Json(name = "reviews_text_count")
    val reviewsTextCount: Int?,
    @Json(name = "saturated_color")
    val saturatedColor: String?,
    @Json(name = "short_screenshots")
    val shortScreenshots: List<ShortScreenshotResponse?>?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "stores")
    val stores: List<StoreResponse?>?,
    @Json(name = "suggestions_count")
    val suggestionsCount: Int?,
    @Json(name = "tags")
    val tags: List<TagResponse?>?,
    @Json(name = "tba")
    val tba: Boolean?,
    @Json(name = "updated")
    val updated: String?,
  ) {
    @JsonClass(generateAdapter = true)
    data class AddedByStatusResponse(
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
      val yet: Int?,
    )

    @JsonClass(generateAdapter = true)
    data class EsrbRatingResponse(
      @Json(name = "id")
      val id: Int?,
      @Json(name = "name")
      val name: String?,
      @Json(name = "slug")
      val slug: String?,
    )

    @JsonClass(generateAdapter = true)
    data class GenreResponse(
      @Json(name = "games_count")
      val gamesCount: Int?,
      @Json(name = "id")
      val id: Int?,
      @Json(name = "image_background")
      val imageBackground: String?,
      @Json(name = "name")
      val name: String?,
      @Json(name = "slug")
      val slug: String?,
    )

    @JsonClass(generateAdapter = true)
    data class ParentPlatformResponse(
      @Json(name = "platform")
      val platform: PlatformResponse?,
    ) {
      @JsonClass(generateAdapter = true)
      data class PlatformResponse(
        @Json(name = "id")
        val id: Int?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?,
      )
    }

    @JsonClass(generateAdapter = true)
    data class PlatformResponse(
      @Json(name = "platform")
      val platform: PlatformResponse?,
      @Json(name = "released_at")
      val releasedAt: String?,
      @Json(name = "requirements_en")
      val requirementsEn: RequirementsEnResponse?,
      @Json(name = "requirements_ru")
      val requirementsRu: RequirementsRuResponse?,
    ) {
      @JsonClass(generateAdapter = true)
      data class PlatformResponse(
        @Json(name = "games_count")
        val gamesCount: Int?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image_background")
        val imageBackground: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "slug")
        val slug: String?,
        @Json(name = "year_start")
        val yearStart: Int?,
      )

      @JsonClass(generateAdapter = true)
      data class RequirementsEnResponse(
        @Json(name = "minimum")
        val minimum: String?,
        @Json(name = "recommended")
        val recommended: String?,
      )

      @JsonClass(generateAdapter = true)
      data class RequirementsRuResponse(
        @Json(name = "minimum")
        val minimum: String?,
        @Json(name = "recommended")
        val recommended: String?,
      )
    }

    @JsonClass(generateAdapter = true)
    data class RatingResponse(
      @Json(name = "count")
      val count: Int?,
      @Json(name = "id")
      val id: Int?,
      @Json(name = "percent")
      val percent: Double?,
      @Json(name = "title")
      val title: String?,
    )

    @JsonClass(generateAdapter = true)
    data class ShortScreenshotResponse(
      @Json(name = "id")
      val id: Int?,
      @Json(name = "image")
      val image: String?,
    )

    @JsonClass(generateAdapter = true)
    data class StoreResponse(
      @Json(name = "id")
      val id: Int?,
      @Json(name = "store")
      val store: StoreResponse?,
    ) {
      @JsonClass(generateAdapter = true)
      data class StoreResponse(
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
        val slug: String?,
      )
    }

    @JsonClass(generateAdapter = true)
    data class TagResponse(
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
      val slug: String?,
    )
  }
}
