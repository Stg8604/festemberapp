package edu.nitt.delta.core.model.payload.Gallery

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.payload.Informals.Sizes
import edu.nitt.delta.core.storage.FestDbUtils.KEY_EVENT_INFO
import edu.nitt.delta.core.storage.FestDbUtils.KEY_FILENAME
import edu.nitt.delta.core.storage.FestDbUtils.KEY_FILESIZE
import edu.nitt.delta.core.storage.FestDbUtils.KEY_HEIGHT
import edu.nitt.delta.core.storage.FestDbUtils.KEY_ID
import edu.nitt.delta.core.storage.FestDbUtils.KEY_MIME_TYPE
import edu.nitt.delta.core.storage.FestDbUtils.KEY_SIZES
import edu.nitt.delta.core.storage.FestDbUtils.KEY_TESTIMONIALS
import edu.nitt.delta.core.storage.FestDbUtils.KEY_URL
import edu.nitt.delta.core.storage.FestDbUtils.KEY_WIDTH
import edu.nitt.delta.core.storage.FestDbUtils.TABLE_GALLERY
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_GALLERY)
class GalleryData(
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = KEY_ID)
  val id: String,

  @ColumnInfo(name = KEY_EVENT_INFO)
  @SerializedName("eventInfo")
  val eventInfo: String,

  @ColumnInfo(name = KEY_TESTIMONIALS)
  @SerializedName("testimonial")
  val testimonials: String,

  @ColumnInfo(name = KEY_FILENAME)
  @SerializedName("filename")
  val filename: String,

  @ColumnInfo(name = KEY_MIME_TYPE)
  @SerializedName("mimeType")
  val mimeType: String,

  @ColumnInfo(name = KEY_FILESIZE)
  @SerializedName("filesize")
  val filesize: Int,

  @ColumnInfo(name = KEY_WIDTH)
  @SerializedName("width")
  val width: Int,

  @ColumnInfo(name = KEY_HEIGHT)
  @SerializedName("height")
  val height: Int,

  @ColumnInfo(name = KEY_SIZES)
  @SerializedName("sizes")
  val sizes: Sizes,

  @SerializedName("createdAt")
  val createdAt: String,

  @SerializedName("updatedAt")
  val updatedAt: String,

  @ColumnInfo(name = KEY_URL)
  @SerializedName("url")
  val url: String
) : Parcelable
