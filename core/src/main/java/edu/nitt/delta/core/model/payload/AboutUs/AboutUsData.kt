package edu.nitt.delta.core.model.payload.AboutUs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class AboutUsData(
  @SerializedName("id")
  val id: String,

  @SerializedName("description")
  val description: String,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable
