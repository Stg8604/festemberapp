package edu.nitt.delta.core.model.event

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.storage.FestDbUtils.KEY_CLUSTER
import edu.nitt.delta.core.storage.FestDbUtils.KEY_CONTACT
import edu.nitt.delta.core.storage.FestDbUtils.KEY_DATE
import edu.nitt.delta.core.storage.FestDbUtils.KEY_DESCRIPTION
import edu.nitt.delta.core.storage.FestDbUtils.KEY_END_TIME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_ID
import edu.nitt.delta.core.storage.FestDbUtils.KEY_IMAGE
import edu.nitt.delta.core.storage.FestDbUtils.KEY_LAST_UPDATE_TIME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_LOCATION_X
import edu.nitt.delta.core.storage.FestDbUtils.KEY_LOCATION_Y
import edu.nitt.delta.core.storage.FestDbUtils.KEY_MAX_LIMIT
import edu.nitt.delta.core.storage.FestDbUtils.KEY_NAME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_REGISTERED
import edu.nitt.delta.core.storage.FestDbUtils.KEY_START_TIME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_VENUE
import edu.nitt.delta.core.storage.FestDbUtils.TABLE_EVENTS
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_EVENTS)
class EventData(
  @SerializedName("event_id")
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = KEY_ID)
  val id: Long,

  @SerializedName("event_name")
  @ColumnInfo(name = KEY_NAME)
  val name: String,

  @SerializedName("event_start_time")
  @ColumnInfo(name = KEY_START_TIME)
  val startTime: String,

  @SerializedName("event_end_time")
  @ColumnInfo(name = KEY_END_TIME)
  val endTime: String,

  @SerializedName("event_venue")
  @ColumnInfo(name = KEY_VENUE)
  val venue: String,

  @SerializedName("event_desc")
  @ColumnInfo(name = KEY_DESCRIPTION)
  val description: String,

  @SerializedName("event_last_update_time")
  @ColumnInfo(name = KEY_LAST_UPDATE_TIME)
  val lastUpdateTime: String,

  @SerializedName("event_loc_x")
  @ColumnInfo(name = KEY_LOCATION_X)
  val locationX: String,

  @SerializedName("event_loc_y")
  @ColumnInfo(name = KEY_LOCATION_Y)
  val locationY: String,

  @SerializedName("event_max_limit")
  @ColumnInfo(name = KEY_MAX_LIMIT)
  val maxLimit: String,

  @SerializedName("event_cluster")
  @ColumnInfo(name = KEY_CLUSTER)
  val cluster: String,

  @SerializedName("event_date")
  @ColumnInfo(name = KEY_DATE)
  val date: String,

  @SerializedName("event_contact")
  @ColumnInfo(name = KEY_CONTACT)
  val contact: String,

  @SerializedName("event_image")
  @ColumnInfo(name = KEY_IMAGE)
  val image: String,

  @ColumnInfo(name = KEY_REGISTERED)
  val isRegistered: Boolean

) : Parcelable {
  override fun equals(event: Any?): Boolean {
    require(event is EventData)
    return this.id == event.id &&
      this.name == event.name &&
      this.startTime == event.startTime &&
      this.endTime == event.endTime &&
      this.venue == event.venue &&
      this.description == event.description &&
      this.lastUpdateTime == event.lastUpdateTime &&
      this.locationX == event.locationX &&
      this.locationY == event.locationY &&
      this.image == event.image &&
      this.maxLimit == event.maxLimit &&
      this.cluster == event.cluster &&
      this.date == event.date &&
      this.contact == event.contact
  }
}
