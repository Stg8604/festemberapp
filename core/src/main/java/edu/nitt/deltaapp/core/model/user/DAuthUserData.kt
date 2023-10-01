package edu.nitt.deltaapp.core.model.user

import com.google.gson.annotations.SerializedName

data class DAuthUserData(
  @SerializedName("email")
  val email: String,

  @SerializedName("id")
  val id: Int,

  @SerializedName("name")
  val name: String,

  @SerializedName("phoneNumber")
  val phoneNumber: String,

  @SerializedName("gender")
  val gender: String,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String,

  @SerializedName("batch")
  val batch: String
)
