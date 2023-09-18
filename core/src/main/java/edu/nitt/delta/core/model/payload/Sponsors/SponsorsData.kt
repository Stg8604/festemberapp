package edu.nitt.delta.core.model.payload.Sponsors

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.payload.Informals.Image
import kotlinx.android.parcel.Parcelize

@Parcelize
class SponsorsData(
  @SerializedName("id")
  val id: String,

  @SerializedName("SponsorName")
  val sponsorname: String,

  @SerializedName("SponsorImage")
  val image: Image,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable
