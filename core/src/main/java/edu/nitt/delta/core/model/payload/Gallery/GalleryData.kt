package edu.nitt.delta.core.model.payload.Gallery

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.payload.Informals.Sizes
import kotlinx.android.parcel.Parcelize

@Parcelize
class GalleryData(
  @SerializedName("id")
  val id: String,

  @SerializedName("eventInfo")
  val eventInfo: String,

  @SerializedName("testimonials")
  val testimonials: String,

  @SerializedName("filename")
  val filename: String,

  @SerializedName("mimeType")
  val mimeType: String,

  @SerializedName("filesize")
  val filesize: Int,

  @SerializedName("width")
  val width: Int,

  @SerializedName("height")
  val height: Int,

  @SerializedName("sizes")
  val sizes: Sizes,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String,

  @SerializedName("url")
  val url: String
) : Parcelable
