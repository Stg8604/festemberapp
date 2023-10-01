package edu.nitt.deltaapp.core.model.payload.Workshops

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.payload.Informals.Contact
import edu.nitt.deltaapp.core.model.payload.Informals.Image
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_CONTACT
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_DAY
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_DESCRIPTION
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_ID
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_IMAGE
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_NAME
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_REGISTRATAION_LINK
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_START_TIME
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_VENUE
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_WORKSHOPS
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_WORKSHOPS)
class WorkshopData(
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
