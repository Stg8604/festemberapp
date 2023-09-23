package edu.nitt.delta.core.event

import android.util.Log
import edu.nitt.delta.core.PAYLOAD_BASE_URL
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.api.Routes.ABOUTUS
import edu.nitt.delta.core.api.Routes.CLUSTERS
import edu.nitt.delta.core.api.Routes.GALLERY
import edu.nitt.delta.core.api.Routes.GUEST_LECTURES
import edu.nitt.delta.core.api.Routes.HOSPITALITY
import edu.nitt.delta.core.api.Routes.INFORMALS
import edu.nitt.delta.core.api.Routes.SPONSORS
import edu.nitt.delta.core.api.Routes.WORKSHOPS
import edu.nitt.delta.core.model.Result
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.core.model.payload.AboutUs.AboutUsData
import edu.nitt.delta.core.model.payload.Clusters.ClustersData
import edu.nitt.delta.core.model.payload.Gallery.GalleryData
import edu.nitt.delta.core.model.payload.GuestLectures.GuestData
import edu.nitt.delta.core.model.payload.Hospitality.HospitalityData
import edu.nitt.delta.core.model.payload.Informals.InformalsData
import edu.nitt.delta.core.model.payload.Sponsors.SponsorsData
import edu.nitt.delta.core.model.payload.Workshops.WorkshopData
import edu.nitt.delta.core.storage.EventsDao
import org.json.JSONArray
import javax.inject.Inject

class EventRepository @Inject constructor(private val festApi: FestApiInterface, private val eventsDao: EventsDao) {

  val events = eventsDao.getAllEvents()
  val clusters = eventsDao.getClusterNames()
  val TAG = "EventRepository"

  suspend fun getEvents(): Result<List<EventData>> = try {
    val response = festApi.getEventsData()

    if (response.statusCode == 200 && response.message != null) {
      eventsDao.addEvents(response.message!!)
      Result.build { response.message!! }
    } else {
      Log.e(TAG, "request returned with status : ${response.statusCode} and message : ${response.message}")
      Result.build<List<EventData>> { throw Exception("Error getting the events") }
    }
  } catch (e: Exception) {
    Result.build<List<EventData>> { throw e }
  }

  suspend fun getSubscribedEvents(userId: Long, token: String): Result<List<String>> = try {
    val response = festApi.getSubscribedEvents(userId, token)
    if (response.statusCode == 200 && response.message != null) {
      Result.build { response.message!! }
    } else {
      Log.e(TAG, "request returned with status ${response.statusCode} and message : ${response.message}")
      Result.build<List<String>> { throw Exception("Error fetching Subscribed Events") }
    }
  } catch (e: Exception) {
    Result.build<List<String>> { throw e }
  }

  suspend fun subscribeEvent(userID: Long, eventID: Long, token: String): Result<String> = try {
    val response = festApi.subscribeEvent(userID, eventID, token)
    if (response.statusCode == 200 && response.message != null) {
      eventsDao.subscribeForEvent(eventID)
      Result.build { response.message!! }
    } else {
      Log.e(TAG, "request returned with status ${response.statusCode} and message : ${response.message}")
      Result.build<String> { throw Exception("Error Subscribing Event") }
    }
  } catch (e: Exception) {
    Result.build<String> { throw e }
  }

  suspend fun unsubscribeEvent(userID: Long, eventID: Long, token: String): Result<String> = try {
    val response = festApi.unSubscribeEvent(userID, eventID, token)
    if (response.statusCode == 200 && response.message != null) {
      eventsDao.unsubscribeForEvent(eventID)
      Result.build { response.message!! }
    } else {
      Log.e(TAG, "request returned with status ${response.statusCode} and message : ${response.message}")
      Result.build<String> { throw Exception("Error Unsubscribing Event") }
    }
  } catch (e: Exception) {
    Result.build<String> { throw e }
  }

  suspend fun getGuestLectures(): Result<List<GuestData>> = try {
    val response = festApi.getGuestLectureData(PAYLOAD_BASE_URL + GUEST_LECTURES)

    if (response.guests != null) {
      Result.build { response.guests }
    } else {
      Log.e(TAG, "Failed to get guest lectures")
      Result.build<List<GuestData>> { throw Exception("Error getting the guest lectures") }
    }
  } catch (e: Exception) {
    Result.build<List<GuestData>> { throw e }
  }

  suspend fun getHospitality(): Result<List<HospitalityData>> = try {
    val response = festApi.getHospitalityData(PAYLOAD_BASE_URL + HOSPITALITY)
    Log.e(TAG, response.Hospi[0].toString())
    if (response.Hospi != null) {
      Result.build { response.Hospi }
    } else {
      Log.e(TAG, "Failed to get Hospitality Details")
      Result.build<List<HospitalityData>> { throw Exception("Error getting the Hospitality Details") }
    }
  } catch (e: Exception) {
    Result.build<List<HospitalityData>> { throw e }
  }

  suspend fun getInformals(): Result<List<InformalsData>> = try {
    val response = festApi.getInformalsData(PAYLOAD_BASE_URL + INFORMALS)
    if (response.entry != null) {
      Result.build { response.entry }
    } else {
      Log.e(TAG, "Failed to get Informals Details")
      Result.build<List<InformalsData>> { throw Exception("Error getting the Informals Details") }
    }
  } catch (e: Exception) {
    Result.build<List<InformalsData>> { throw e }
  }

  suspend fun getSponsors(): Result<List<SponsorsData>> = try {
    val response = festApi.getSponsorsData(PAYLOAD_BASE_URL + SPONSORS)
    if (response.entry != null) {
      Result.build { response.entry }
    } else {
      Log.e(TAG, "Failed to get Sponsors Details")
      Result.build<List<SponsorsData>> { throw Exception("Error getting the Sponsors Details") }
    }
  } catch (e: Exception) {
    Result.build<List<SponsorsData>> { throw e }
  }

  suspend fun getAboutUs(): Result<List<AboutUsData>> = try {
    val response = festApi.getAboutUsData(PAYLOAD_BASE_URL + ABOUTUS)
    if (response.entry != null) {
      Result.build { response.entry }
    } else {
      Log.e(TAG, "Failed to get About Us Details")
      Result.build<List<AboutUsData>> { throw Exception("Error getting the About Us Details") }
    }
  } catch (e: Exception) {
    Result.build<List<AboutUsData>> { throw e }
  }

  suspend fun getWorkshops(): Result<List<WorkshopData>> = try {
    val response = festApi.getWorkshopData(PAYLOAD_BASE_URL + WORKSHOPS)
    if (response.entry != null) {
      Result.build { response.entry }
    } else {
      Result.build<List<WorkshopData>> { throw Exception("Error getting the Workshop Details") }
    }
  } catch (e: Exception) {
    Result.build<List<WorkshopData>> { throw e }
  }

  suspend fun getClusters(): Result<List<ClustersData>> = try {
    val response = festApi.getClustersData(PAYLOAD_BASE_URL + CLUSTERS)
    if (response.clusters != null) {
      for (cluster in response.clusters) {
        for (event in cluster.eventDetails) {
          val jsonFormat = jsonify(event.rawFormat.toString())
          val jsonJudgingCriteria = jsonify(event.rawJudgingCriteria.toString())

          event.htmlFormat = jsonArrayToHTML(JSONArray(jsonFormat))
          event.htmlJudgingCriteria = jsonArrayToHTML(JSONArray(jsonJudgingCriteria))
        }
      }

      Result.build { response.clusters }
    } else {
      Result.build<List<ClustersData>> { throw Exception("Error getting the Cluster & Event Details") }
    }
  } catch (e: Exception) {
    Result.build<List<ClustersData>> { throw e }
  }

  suspend fun getGallery(): Result<List<GalleryData>> = try {
    val response = festApi.getGalleryData(PAYLOAD_BASE_URL + GALLERY)
    if (response.entry != null) {
      Result.build { response.entry }
    } else {
      Result.build<List<GalleryData>> { throw Exception("Error getting the Gallery Details") }
    }
  } catch (e: Exception) {
    Result.build<List<GalleryData>> { throw e }
  }

  private fun jsonify(text: String): String {

    return text.replace("{", "{'")
      .replace("}", "'}")
      .replace("=", "': '")
      .replace(", ", "', '")
      .replace("'{", "{")
      .replace("}'", "}")
      .replace("'[", "[")
      .replace("]'", "]")
  }
  private fun jsonArrayToHTML(jsonArray: JSONArray): String {

    // Add other tags if required
    val attributeToTag = mapOf(
      "bold" to "strong",
      "code" to "code",
      "italic" to "em",
      "underline" to "u"
    )

    val len = jsonArray.length()

    if (len == 0) {
      return ""
    }

    var total = ""

    for (i in 0 until len) {
      val obj = jsonArray.getJSONObject(i)

      if (obj.has("text")) {
        var text = "<span>${obj.getString("text")}</span>"
        attributeToTag.keys.forEach() { attribute ->
          if (obj.has(attribute)) {
            val tag = attributeToTag[attribute]
            if (tag != null) {
              text = "<$tag>$text</$tag>"
            }
          }
        }
        total += text
      }

      if (obj.has("type") and obj.has("children")) {
        val type = if (obj.has("type")) obj.getString("type") else ""
        val tag = if (type == "") "div" else type
        val url = if (obj.has("url")) obj.getString("url") else ""
        val children = jsonArrayToHTML(obj.getJSONArray("children"))

        total += if ((tag == "link") and (url != "")) {
          "<a href=$url>$children</a>"
        } else {
          "<$tag>$children</$tag>"
        }
      } else if (obj.has("children")) {
        val children = jsonArrayToHTML(obj.getJSONArray("children"))
        total += "<div>$children</div>"
      }
    }

    return total
  }
}
