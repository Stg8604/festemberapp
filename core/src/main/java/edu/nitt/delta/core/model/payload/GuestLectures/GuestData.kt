package edu.nitt.delta.core.model.payload.GuestLectures

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.payload.Informals.Contact
import edu.nitt.delta.core.model.payload.Informals.Image
import kotlinx.android.parcel.Parcelize

@Parcelize
class GuestData(
  @SerializedName("id")
  val id: String,

  @SerializedName("Image")
  val image: Image,

  @SerializedName("Name")
  val name: String,

  @SerializedName("Description")
  val description: String,

  @SerializedName("Day")
  val day: Int,

  @SerializedName("Venue")
  val venue: String,

  @SerializedName("Time")
  val time: String,

  @SerializedName("RegistrationLink")
  val registrationLink: String,

  @SerializedName("Contact")
  val contact: List<Contact>,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable
