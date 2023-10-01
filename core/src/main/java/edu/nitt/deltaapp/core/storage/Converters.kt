package edu.nitt.deltaapp.core.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import edu.nitt.deltaapp.core.model.payload.Clusters.EventDetail
import edu.nitt.deltaapp.core.model.payload.Clusters.Prize
import edu.nitt.deltaapp.core.model.payload.Informals.Contact
import edu.nitt.deltaapp.core.model.payload.Informals.Image
import edu.nitt.deltaapp.core.model.payload.Informals.Sizes

class Converters {
  @TypeConverter
  fun fromImage(image: Image): String {
    return Gson().toJson(image)
  }

  @TypeConverter
  fun toImage(imageJson: String): Image {
    return Gson().fromJson(imageJson, Image::class.java)
  }

  @TypeConverter
  fun fromListContact(contact: List<Contact>): String {
    return Gson().toJson(contact)
  }

  @TypeConverter
  fun toListContact(contactJson: String): List<Contact> {
    return Gson().fromJson(contactJson, Array<Contact>::class.java).toList()
  }

  @TypeConverter
  fun fromSizes(sizes: Sizes): String {
    return Gson().toJson(sizes)
  }

  @TypeConverter
  fun toSizes(sizesJson: String): Sizes {
    return Gson().fromJson(sizesJson, Sizes::class.java)
  }

  @TypeConverter
  fun fromListPrize(prize: List<Prize>): String {
    return Gson().toJson(prize)
  }

  @TypeConverter
  fun toListPrize(prizeJson: String): List<Prize> {
    return Gson().fromJson(prizeJson, Array<Prize>::class.java).toList()
  }

  @TypeConverter
  fun fromListEventDetail(eventDetail: List<EventDetail>): String {
    return Gson().toJson(eventDetail)
  }

  @TypeConverter
  fun toListEventDetail(eventDetailJson: String): List<EventDetail> {
    return Gson().fromJson(eventDetailJson, Array<EventDetail>::class.java).toList()
  }

  @TypeConverter
  fun fromAny(any: Any): String {
    return Gson().toJson(any)
  }

  @TypeConverter
  fun toAny(anyJson: String): Any {
    return Gson().fromJson(anyJson, Any::class.java)
  }
}
