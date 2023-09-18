package edu.nitt.delta.core.model.payload.Informals

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class InformalsData(
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

@Parcelize
class Contact(
  @SerializedName("ContactName")
  val contactName: String,

  @SerializedName("ContactNumber")
  val contactNumber: Long,

  @SerializedName("id")
  val id: String
) : Parcelable

@Parcelize
class Image(
  @SerializedName("id")
  val id: String,
  @SerializedName("alt")
  val alt: String,
  @SerializedName("filename")
  val filename: String,
  @SerializedName("mimeType")
  val mimeType: String,
  @SerializedName("filesize")
  val filesize: Long,
  @SerializedName("width")
  val width: Long,
  @SerializedName("height")
  val height: Long,
  @SerializedName("sizes")
  val sizes: Sizes,
  @SerializedName("createdAt")
  val createdAt: String,
  @SerializedName("updatedAt")
  val updatedAt: String,
  @SerializedName("url")
  val url: String
) : Parcelable

@Parcelize
class Sizes(
  @SerializedName("mobile")
  val mobile: Mobile,
  @SerializedName("web")
  val web: Mobile,
  @SerializedName("tablet")
  val tablet: Mobile
) : Parcelable

@Parcelize
class Mobile(
  @SerializedName("width")
  val width: Long,
  @SerializedName("height")
  val height: Long,
  @SerializedName("mimeType")
  val mimeType: String,
  @SerializedName("filesize")
  val filesize: Long,
  @SerializedName("filename")
  val filename: String,
  @SerializedName("url")
  val url: String
) : Parcelable
