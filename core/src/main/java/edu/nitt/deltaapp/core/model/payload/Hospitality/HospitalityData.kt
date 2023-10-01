package edu.nitt.deltaapp.core.model.payload.Hospitality

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_ACCOMODATION
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_CONTACTS
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_FAQS
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_HOW_TO_REACH
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_ID
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_INSTRUCTION
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_HOSPITALITY
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = TABLE_HOSPITALITY)
class HospitalityData(
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = KEY_ID)
  val id: String,

  @SerializedName("Instruction")
  val rawInstruction: @RawValue Any,

  @SerializedName("HowToReach")
  val rawHowToReach: @RawValue Any,

  @SerializedName("Accomodation")
  val rawAccommodation: @RawValue Any,

  @SerializedName("Contacts")
  val rawContacts: @RawValue Any,

  @SerializedName("FAQs")
  val rawFAQs: @RawValue Any,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String,

  @ColumnInfo(name = KEY_INSTRUCTION)
  var htmlInstruction: String = "<div></div>",

  @ColumnInfo(name = KEY_HOW_TO_REACH)
  var htmlHowToReach: String = "<div></div>",

  @ColumnInfo(name = KEY_ACCOMODATION)
  var htmlAccommodation: String = "<div></div>",

  @ColumnInfo(name = KEY_CONTACTS)
  var htmlContacts: String = "<div></div>",

  @ColumnInfo(name = KEY_FAQS)
  var htmlFAQs: String = "<div></div>"
) : Parcelable
