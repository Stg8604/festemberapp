package edu.nitt.delta.core.model.payload.AboutUs

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.storage.FestDbUtils.KEY_DESCRIPTION
import edu.nitt.delta.core.storage.FestDbUtils.KEY_ID
import edu.nitt.delta.core.storage.FestDbUtils.TABLE_ABOUT_US
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_ABOUT_US)
class AboutUsData(
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = KEY_ID)
  val id: String,

  @SerializedName("description")
  @ColumnInfo(name = KEY_DESCRIPTION)
  val description: String,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable
