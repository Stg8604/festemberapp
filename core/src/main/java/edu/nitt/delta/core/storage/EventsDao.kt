package edu.nitt.delta.core.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.core.storage.FestDbUtils.KEY_CLUSTER
import edu.nitt.delta.core.storage.FestDbUtils.KEY_ID
import edu.nitt.delta.core.storage.FestDbUtils.KEY_REGISTERED
import edu.nitt.delta.core.storage.FestDbUtils.TABLE_EVENTS

@Dao
interface EventsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addEvent(event: EventData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addEvents(eventList: List<EventData>)

  @Query("DELETE FROM $TABLE_EVENTS")
  suspend fun clearTable()

  @Query("UPDATE $TABLE_EVENTS SET $KEY_REGISTERED = 1 WHERE $KEY_ID = :eventId ")
  suspend fun subscribeForEvent(eventId: Long): Int

  @Query("UPDATE $TABLE_EVENTS SET $KEY_REGISTERED = 0 WHERE $KEY_ID = :eventId ")
  suspend fun unsubscribeForEvent(eventId: Long): Int

  @Query("SELECT DISTINCT $KEY_CLUSTER FROM $TABLE_EVENTS")
  fun getClusterNames(): LiveData<List<String>>

  @Query("SELECT * FROM  $TABLE_EVENTS WHERE $KEY_ID = :eventId  LIMIT 1")
  fun getEvent(eventId: Long): LiveData<EventData>

  @Query("SELECT * FROM $TABLE_EVENTS")
  fun getAllEvents(): LiveData<List<EventData>>
}
