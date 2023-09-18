package edu.nitt.delta.core.model.payload.Hospitality

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class HospitalityData(

  @SerializedName("id")
  val id: String,

  @SerializedName("Instruction")
  val instruction: String,

  @SerializedName("HowToReach")
  val howToReach: String,

  @SerializedName("Accomodation")
  val accomodation: String,

  @SerializedName("Contacts")
  val contacts: String,

  @SerializedName("FAQs")
  val faQs: String,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable
