package edu.nitt.delta.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.nitt.delta.core.database.FestDbUtils.KEY_CLUSTER
import edu.nitt.delta.core.database.FestDbUtils.KEY_CONTACT
import edu.nitt.delta.core.database.FestDbUtils.KEY_DATE
import edu.nitt.delta.core.database.FestDbUtils.KEY_DESCRIPTION
import edu.nitt.delta.core.database.FestDbUtils.KEY_END_TIME
import edu.nitt.delta.core.database.FestDbUtils.KEY_ID
import edu.nitt.delta.core.database.FestDbUtils.KEY_IMAGE
import edu.nitt.delta.core.database.FestDbUtils.KEY_LAST_UPDATE_TIME
import edu.nitt.delta.core.database.FestDbUtils.KEY_LOCATION_X
import edu.nitt.delta.core.database.FestDbUtils.KEY_LOCATION_Y
import edu.nitt.delta.core.database.FestDbUtils.KEY_MAX_LIMIT
import edu.nitt.delta.core.database.FestDbUtils.KEY_NAME
import edu.nitt.delta.core.database.FestDbUtils.KEY_REGISTERED
import edu.nitt.delta.core.database.FestDbUtils.KEY_START_TIME
import edu.nitt.delta.core.database.FestDbUtils.KEY_VENUE
import edu.nitt.delta.core.database.FestDbUtils.TABLE_EVENTS

@Entity(tableName = TABLE_EVENTS)
class EventData(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = KEY_ID)
  val id: Long,

  @ColumnInfo(name = KEY_NAME)
  val name: String,

  @ColumnInfo(name = KEY_START_TIME)
  val startTime: String,

  @ColumnInfo(name = KEY_END_TIME)
  val endTime: String,

  @ColumnInfo(name = KEY_VENUE)
  val venue: String,

  @ColumnInfo(name = KEY_DESCRIPTION)
  val description: String,

  @ColumnInfo(name = KEY_LAST_UPDATE_TIME)
  val lastUpdateTime: String,

  @ColumnInfo(name = KEY_LOCATION_X)
  val locationX: String,

  @ColumnInfo(name = KEY_LOCATION_Y)
  val locationY: String,

  @ColumnInfo(name = KEY_MAX_LIMIT)
  val maxLimit: String,

  @ColumnInfo(name = KEY_CLUSTER)
  val cluster: String,

  @ColumnInfo(name = KEY_DATE)
  val date: String,

  @ColumnInfo(name = KEY_REGISTERED)
  val registered: String,

  @ColumnInfo(name = KEY_CONTACT)
  val contact: String,

  @ColumnInfo(name = KEY_IMAGE)
  val image: String

)
