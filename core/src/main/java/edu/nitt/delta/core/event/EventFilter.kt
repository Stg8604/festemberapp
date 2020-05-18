package edu.nitt.delta.core.event

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import edu.nitt.delta.core.model.event.EventData
import java.text.ParseException

class EventFilter private constructor() {
  private val clusters = arrayListOf<String>()
  private var regex: Regex = Regex(".*")
  private var startDay: Int = 0
  private var endDay: Int = 3
  private var startTime: String = "00:00:00"
  private var endTime: String = "23:59:59"
  private var location: Pair<Double, Double> = Pair(78.8147194, 10.7620306) // location in NITT
  private var radius: Float = 10000f // 10km, safe distance to cover entire campus if location is not passes

  private fun checkCluster(eventCluster: String): Boolean {
    return if (clusters.isNotEmpty()) eventCluster.trim().toLowerCase() in clusters
    else true
  }

  private fun checkRegex(eventData: EventData): Boolean {
    return regex.containsMatchIn(eventData.name) ||
        regex.containsMatchIn(eventData.description) ||
        regex.containsMatchIn(eventData.cluster) ||
        regex.containsMatchIn(eventData.venue)
  }

  private fun checkEventDateTime(eventStartTime: String, eventDate: String): Boolean = try {
    val startDate = startDay.getDateforDay()
    startDate += startTime.getDateFromTimeString()!!

    val endDate = endDay.getDateforDay()
    endDate += endTime.getDateFromTimeString()!!

    val eventDate = eventDate.getDateFromDateString()!!
    eventDate += eventStartTime.getDateFromTimeString()!!

    eventDate.after(startDate) && eventDate.before(endDate) // check if the event starts in the given interval
  } catch (e: ParseException) {
    e.printStackTrace()
    false
  }

  private fun checkLocationAndRadius(eventLocation: Pair<Double, Double>): Boolean {
    val distance = FloatArray(1)
    Location.distanceBetween(eventLocation.second, eventLocation.first, location.second, location.first, distance)
    return distance[0] <= radius
  }

  private fun checkEvent(eventData: EventData): Boolean {
    return checkCluster(eventData.cluster) &&
        checkRegex(eventData) &&
        checkEventDateTime(eventData.startTime, eventData.date) &&
        checkLocationAndRadius(Pair(eventData.locationX.toDouble(), eventData.locationY.toDouble()))
  }

  fun filter(eventsLiveData: LiveData<List<EventData>>): LiveData<List<EventData>> {
    val filteredEvents = Transformations.map(eventsLiveData) { events ->
      events.filter { event -> checkEvent(event) }
    }

    return Transformations.distinctUntilChanged(filteredEvents)
  }

  class Builder() {
    private val eventFilter = EventFilter()

    fun setClusters(vararg cluster: String): Builder {
      eventFilter.clusters.addAll(cluster.map { it.trim().toLowerCase() })
      return this
    }

    fun setRegex(regex: Regex): Builder {
      eventFilter.regex = regex
      return this
    }

    /**
     * set the day of the event
     *
     * @param startDay filter events on of after this day
     * @param endDay filter events before this day. leave blank if only one day
     */
    fun setDay(startDay: Int, endDay: Int = startDay) {
      require(startDay in 0..3 && endDay in 0..3 && startDay <= endDay)
      eventFilter.startDay = startDay
      eventFilter.endDay = endDay
    }

    /**
     * Set Filter get events after given time
     *
     * @param startTime in format "HH:mm:ss"
     */
    fun setStartTimestamp(startTime: String): Builder {
      require(eventFilter.startDay != null) { "call the setDay function before this" }
      eventFilter.startTime = startTime
      return this
    }

    /**
     * Set Filter get events before given time
     *
     * @param endTime in format "HH:mm:ss"
     */
    fun setEndTimestamp(endTime: String): Builder {
      require(eventFilter.endDay != null) { "call the setDay function before this" }
      eventFilter.endTime = endTime
      return this
    }

    /**
     * check for events around given location
     *
     * @param location (locationX, LocationY) or (Longitude, Latitude)
     * @param radius in meters
     */
    fun setLocation(location: Pair<Double, Double>, radius: Float): Builder {
      eventFilter.location = location
      eventFilter.radius = radius
      return this
    }

    fun build() = eventFilter
  }
}
