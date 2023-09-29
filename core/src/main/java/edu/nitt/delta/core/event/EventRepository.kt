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
import edu.nitt.delta.core.storage.PayloadDao
import org.json.JSONArray
import javax.inject.Inject

class EventRepository @Inject constructor(private val festApi: FestApiInterface, private val payloadDao: PayloadDao) {

  val aboutUs = payloadDao.getAboutUs()
  val workshops = payloadDao.getWorkshops()
  val sponsors = payloadDao.getSponsors()
  val informals = payloadDao.getInformals()
  val hospitality = payloadDao.getHospitality()
  val guestLectures = payloadDao.getGuestLectures()
  val gallery = payloadDao.getGallery()
  val clusterNames = payloadDao.getClusterNames()
  val clusterEvents = payloadDao.getClusterEvents()
  val TAG = "EventRepository"

  suspend fun getEvents(): Result<List<EventData>> = try {
    val response = festApi.getEventsData()

    if (response.statusCode == 200 && response.message != null) {
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
      payloadDao.addGuestLecturesList(response.guests)
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
    if ((response.Hospi != null) and (response.Hospi.isNotEmpty())) {
      if (response.Hospi[0].rawInstruction != null) {
        val jsonInstruction = jsonify(response.Hospi[0].rawInstruction.toString())
        response.Hospi[0].htmlInstruction = jsonArrayToHTML(JSONArray(jsonInstruction))
      }
      if (response.Hospi[0].rawHowToReach != null) {
        val jsonHowToReach = jsonify(response.Hospi[0].rawHowToReach.toString())
        response.Hospi[0].htmlHowToReach = jsonArrayToHTML(JSONArray(jsonHowToReach))
      }
      if (response.Hospi[0].rawAccommodation != null) {
        val jsonAccommodation = jsonify(response.Hospi[0].rawAccommodation.toString())
        response.Hospi[0].htmlAccommodation = jsonArrayToHTML(JSONArray(jsonAccommodation))
      }
      if (response.Hospi[0].rawContacts != null) {
        val jsonContacts = jsonify(response.Hospi[0].rawContacts.toString())
        response.Hospi[0].htmlContacts = jsonArrayToHTML(JSONArray(jsonContacts))
      }
      if (response.Hospi[0].rawFAQs != null) {
        val jsonFAQs = jsonify(response.Hospi[0].rawFAQs.toString())
        response.Hospi[0].htmlFAQs = jsonArrayToHTML(JSONArray(jsonFAQs))
      }
      payloadDao.addHospitalityList(response.Hospi)
      Result.build { response.Hospi }
    } else {
      Result.build<List<HospitalityData>> { throw Exception("Error getting the Hospitality Details") }
    }
  } catch (e: Exception) {
    Result.build<List<HospitalityData>> { throw e }
  }

  suspend fun getInformals(): Result<List<InformalsData>> = try {
    val response = festApi.getInformalsData(PAYLOAD_BASE_URL + INFORMALS)
    if (response.entry != null) {
      payloadDao.addInformalsList(response.entry)
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
      payloadDao.addSponsorsList(response.entry)
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
      payloadDao.addAboutUsList(response.entry)
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
      payloadDao.addWorkshopList(response.entry)
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
          if (event.rawFormat != null) {
            val jsonFormat = jsonify(event.rawFormat.toString())
            event.htmlFormat = jsonArrayToHTML(JSONArray(jsonFormat))
          }
          if (event.rawJudgingCriteria != null) {
            val jsonJudgingCriteria = jsonify(event.rawJudgingCriteria.toString())
            event.htmlJudgingCriteria = jsonArrayToHTML(JSONArray(jsonJudgingCriteria))
          }
        }
      }

      payloadDao.addClustersList(response.clusters)
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
      payloadDao.addGalleryList(response.entry)
      Result.build { response.entry }
    } else {
      Result.build<List<GalleryData>> { throw Exception("Error getting the Gallery Details") }
    }
  } catch (e: Exception) {
    Result.build<List<GalleryData>> { throw e }
  }

  private fun jsonify(rawText: String): String {
    val text = rawText.replace("{", "{'")
      .replace("}", "'}")
      .replace("=", "': '")
      .replace(", ", "', '")
      .replace("'{", "{")
      .replace("}'", "}")
      .replace("'[", "[")
      .replace("]'", "]")

    val pattern = "{'text': "
    var temp = ""
    var jsonText = ""
    var i = 0
    var j = 0
    var flag = false

    while (i < text.length) {
      if (j == pattern.length) {
        j = 0
        flag = true
      }
      if (flag && text[i] != '}') {
        temp += text[i]
      } else if (flag && text[i] == '}') {
        temp = temp.replace("'", "")
        temp = "'$temp'"
        jsonText += "$temp${text[i]}"
        flag = false
        temp = ""
      } else {
        if (text[i] == pattern[j]) {
          jsonText += text[i]
          j++
        } else {
          j = 0
          jsonText += text[i]
        }
      }
      i++
    }
    return jsonText
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
        val tag = if ((type == "") or (type == "indent")) "div" else type
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
