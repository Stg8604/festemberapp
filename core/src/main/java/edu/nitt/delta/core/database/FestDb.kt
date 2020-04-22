package edu.nitt.delta.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.nitt.delta.core.database.FestDbUtils.DATABASE_VERSION
import edu.nitt.delta.core.model.event.EventData

@Database(entities = [EventData::class], version = DATABASE_VERSION, exportSchema = false)
abstract class FestDb : RoomDatabase() {

  abstract fun festDatabaseDao(): EventsDao
}
