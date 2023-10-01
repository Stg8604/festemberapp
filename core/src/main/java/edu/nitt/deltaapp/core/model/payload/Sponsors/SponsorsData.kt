package edu.nitt.deltaapp.core.model.payload.Sponsors

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.payload.Informals.Image
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_ID
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_IMAGE
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_NAME
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_SPONSORS
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_SPONSORS)
class SponsorsData(
  @ColumnInfo(name = KEY_ID)
  @PrimaryKey(autoGenerate = false)
  @SerializedName("id")
  val id: String,

  @ColumnInfo(name = KEY_NAME)
  @SerializedName("SponsorName")
  val sponsorname: String,

  @ColumnInfo(name = KEY_IMAGE)
  @SerializedName("SponsorImage")
  val image: Image,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable
