package edu.nitt.delta.core.model.user

import com.google.gson.annotations.SerializedName

data class CollegeData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("college_name")
  val collegeName: String

) {
  override fun equals(college: Any?): Boolean {
    if (this === college) return true
    if ((college == null) || (javaClass != college.javaClass)) return false

    val otherCollege = college as CollegeData

    if (id != otherCollege.id) return false
    if (collegeName != otherCollege.collegeName) return false

    return hashCode() == otherCollege.hashCode()
  }

  fun toMap(): Map<String, String> =
    mapOf(
      "id" to id.toString(),
      "college_name" to collegeName
    )

  override fun hashCode(): Int {
    var result = id
    result = 31 * result + collegeName.hashCode()
    return result
  }
}
