package edu.nitt.delta.core.model.user

import com.google.gson.annotations.SerializedName

data class ScoreboardData(
  @SerializedName("college_name")
  val college_name: String,

  @SerializedName("points")
  val score: String
) {
  fun toMap(): Map<String, String?> =
    mapOf(
      "college_name" to college_name,
      "score" to score
    )
}
