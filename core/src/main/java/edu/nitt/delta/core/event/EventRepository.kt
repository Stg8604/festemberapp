package edu.nitt.delta.core.event

import android.util.Log
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.model.Result
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.core.storage.EventsDao
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
}
