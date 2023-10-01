package edu.nitt.deltaapp.core.model.payload.Clusters

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.payload.Informals.Contact
import edu.nitt.deltaapp.core.model.payload.Informals.Image
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_CLUSTER_ID
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_EVENT_INFO
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_ID
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_NAME
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_CLUSTERS
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = TABLE_CLUSTERS)
class ClustersData(
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = KEY_ID)
  val id: String,

  @ColumnInfo(name = KEY_CLUSTER_ID)
  @SerializedName("ClusterId")
  val clusterID: Int,

  @ColumnInfo(name = KEY_NAME)
  @SerializedName("ClusterName")
  val clusterName: String,

  @ColumnInfo(name = KEY_EVENT_INFO)
  @SerializedName("EventDetails")
  val eventDetails: List<EventDetail>,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String
) : Parcelable

@Parcelize
class EventDetail(
  @SerializedName("Image")
  val image: Image,

  @SerializedName("Name")
  val name: String,

  @SerializedName("Description")
  val description: String,

  @SerializedName("Format")
  val rawFormat: @RawValue Any,

  @SerializedName("JudgingCriteria")
  val rawJudgingCriteria: @RawValue Any,

  @SerializedName("Day")
  val day: Long,

  @SerializedName("Venue")
  val venue: String,

  @SerializedName("Time")
  val time: String,

  @SerializedName("RegistrationLink")
  val registrationLink: String,

  @SerializedName("RuleBook")
  val ruleBook: String,

  @SerializedName("Contact")
  val contact: List<Contact>,

  @SerializedName("Prize")
  val prize: List<Prize>,

  @SerializedName("id")
  val id: String,

  var htmlFormat: String = "<div></div>",

  var htmlJudgingCriteria: String = "<div></div>"
) : Parcelable

@Parcelize
class Prize(
  @SerializedName("PrizeName")
  val prizeName: String,

  @SerializedName("PrizeAmount")
  val prizeAmount: String,

  @SerializedName("id")
  val id: String
) : Parcelable

data class ClusterName(
  @ColumnInfo(name = KEY_NAME)
  val name: String,

  @ColumnInfo(name = KEY_CLUSTER_ID)
  val clusterID: Int
)
