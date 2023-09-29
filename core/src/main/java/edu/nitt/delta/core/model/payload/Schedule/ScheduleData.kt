package edu.nitt.delta.core.model.payload.Schedule

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.storage.FestDbUtils
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = FestDbUtils.TABLE_SCHEDULE)
class ScheduleData(
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = FestDbUtils.KEY_ID)
  val id: String,

  @SerializedName("Name")
  @ColumnInfo(name = FestDbUtils.KEY_NAME)
  val name: String,

  @SerializedName("Day")
  @ColumnInfo(name = FestDbUtils.KEY_DAY)
  val day: Int,

  @SerializedName("Type")
  @ColumnInfo(name = FestDbUtils.KEY_TYPE)
  val type: String,

  @SerializedName("Venue")
  @ColumnInfo(name = FestDbUtils.KEY_VENUE)
  val venue: String,

  @SerializedName("Time")
  @ColumnInfo(name = FestDbUtils.KEY_START_TIME)
  val time: String
) : Parcelable
