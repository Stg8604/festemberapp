package edu.nitt.delta.core.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.nitt.delta.core.database.FestDbUtils.KEY_ID
import edu.nitt.delta.core.database.FestDbUtils.KEY_REGISTERED
import edu.nitt.delta.core.database.FestDbUtils.TABLE_EVENTS
import edu.nitt.delta.core.model.event.EventData

@Dao
interface EventsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addEvent(event: EventData): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addEvents(eventList: List<EventData>): List<Long>

  @Query("DELETE FROM $TABLE_EVENTS")
  suspend fun clearTable()

  @Query("UPDATE $TABLE_EVENTS SET $KEY_REGISTERED = 1 WHERE $KEY_ID = :eventId ")
  suspend fun registerForEvent(eventId: Long): Int

  @Query("UPDATE $TABLE_EVENTS SET $KEY_REGISTERED = 0 WHERE $KEY_ID = :eventId ")
  suspend fun unregisterForEvent(eventId: Long): Int

  @Query("SELECT * FROM  $TABLE_EVENTS WHERE $KEY_ID = :eventId  LIMIT 1")
  fun getEvent(eventId: Long): LiveData<EventData>

  @Query("SELECT * FROM $TABLE_EVENTS")
  fun getAllEvents(): LiveData<List<EventData>>
}
