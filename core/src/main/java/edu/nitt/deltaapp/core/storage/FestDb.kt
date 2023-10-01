package edu.nitt.deltaapp.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.nitt.deltaapp.core.storage.FestDbUtils.DATABASE_VERSION
import edu.nitt.deltaapp.core.model.payload.AboutUs.AboutUsData
import edu.nitt.deltaapp.core.model.payload.Clusters.ClustersData
import edu.nitt.deltaapp.core.model.payload.Gallery.GalleryData
import edu.nitt.deltaapp.core.model.payload.GuestLectures.GuestData
import edu.nitt.deltaapp.core.model.payload.Hospitality.HospitalityData
import edu.nitt.deltaapp.core.model.payload.Informals.InformalsData
import edu.nitt.deltaapp.core.model.payload.Schedule.ScheduleData
import edu.nitt.deltaapp.core.model.payload.Sponsors.SponsorsData
import edu.nitt.deltaapp.core.model.payload.Workshops.WorkshopData

@Database(
  entities = [
    AboutUsData::class,
    WorkshopData::class,
    SponsorsData::class,
    InformalsData::class,
    HospitalityData::class,
    GuestData::class,
    GalleryData::class,
    ClustersData::class,
    ScheduleData::class
  ],
  version = DATABASE_VERSION,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FestDb : RoomDatabase() {
  abstract fun festDatabaseDao(): PayloadDao
}
