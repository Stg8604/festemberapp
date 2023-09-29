package edu.nitt.delta.core.model.payload.Informals

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.storage.FestDbUtils.KEY_CONTACT
import edu.nitt.delta.core.storage.FestDbUtils.KEY_DAY
import edu.nitt.delta.core.storage.FestDbUtils.KEY_DESCRIPTION
import edu.nitt.delta.core.storage.FestDbUtils.KEY_ID
import edu.nitt.delta.core.storage.FestDbUtils.KEY_IMAGE
import edu.nitt.delta.core.storage.FestDbUtils.KEY_NAME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_REGISTRATAION_LINK
import edu.nitt.delta.core.storage.FestDbUtils.KEY_START_TIME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_VENUE
import edu.nitt.delta.core.storage.FestDbUtils.TABLE_INFORMALS
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_INFORMALS)
class InformalsData(
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = KEY_ID)
  val id: String,

  @ColumnInfo(name = KEY_IMAGE)
  @SerializedName("Image")
  val image: Image,

  @ColumnInfo(name = KEY_NAME)
  @SerializedName("Name")
  val name: String,

  @ColumnInfo(name = KEY_DESCRIPTION)
  @SerializedName("Description")
  val description: String,

  @ColumnInfo(name = KEY_DAY)
  @SerializedName("Day")
  val day: Int,

  @ColumnInfo(name = KEY_VENUE)
  @SerializedName("Venue")
  val venue: String,

  @ColumnInfo(name = KEY_START_TIME)
  @SerializedName("Time")
  val time: String,

  @ColumnInfo(name = KEY_REGISTRATAION_LINK)
  @SerializedName("RegistrationLink")
  val registrationLink: String,

  @ColumnInfo(name = KEY_CONTACT)
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
