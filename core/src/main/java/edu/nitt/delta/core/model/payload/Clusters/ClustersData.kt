package edu.nitt.delta.core.model.payload.Clusters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.payload.Informals.Contact
import edu.nitt.delta.core.model.payload.Informals.Image
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class ClustersData(
  val id: String,

  @SerializedName("docs")
  val clusterID: Long,

  @SerializedName("ClusterName")
  val clusterName: String,

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

  var htmlFormat: String = "",

  var htmlJudgingCriteria: String = ""
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
