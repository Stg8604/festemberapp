package edu.nitt.deltaapp.core.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.nitt.deltaapp.core.model.payload.AboutUs.AboutUsData
import edu.nitt.deltaapp.core.model.payload.Clusters.ClusterName
import edu.nitt.deltaapp.core.model.payload.Clusters.ClustersData
import edu.nitt.deltaapp.core.model.payload.Gallery.GalleryData
import edu.nitt.deltaapp.core.model.payload.GuestLectures.GuestData
import edu.nitt.deltaapp.core.model.payload.Hospitality.HospitalityData
import edu.nitt.deltaapp.core.model.payload.Informals.InformalsData
import edu.nitt.deltaapp.core.model.payload.Schedule.ScheduleData
import edu.nitt.deltaapp.core.model.payload.Sponsors.SponsorsData
import edu.nitt.deltaapp.core.model.payload.Workshops.WorkshopData
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_CLUSTER_ID
import edu.nitt.deltaapp.core.storage.FestDbUtils.KEY_NAME
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_ABOUT_US
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_CLUSTERS
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_GALLERY
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_GUEST_LECTURES
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_HOSPITALITY
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_INFORMALS
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_SCHEDULE
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_SPONSORS
import edu.nitt.deltaapp.core.storage.FestDbUtils.TABLE_WORKSHOPS

@Dao
interface PayloadDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAboutUs(aboutUsData: AboutUsData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAboutUsList(aboutUsData: List<AboutUsData>)

  @Query("SELECT * FROM $TABLE_ABOUT_US")
  fun getAboutUs(): LiveData<List<AboutUsData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addWorkshop(workshopData: WorkshopData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addWorkshopList(workshopData: List<WorkshopData>)

  @Query("SELECT * FROM $TABLE_WORKSHOPS")
  fun getWorkshops(): LiveData<List<WorkshopData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addSponsors(sponsorsData: SponsorsData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addSponsorsList(sponsorsData: List<SponsorsData>)

  @Query("SELECT * FROM $TABLE_SPONSORS")
  fun getSponsors(): LiveData<List<SponsorsData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addInformals(informalsData: InformalsData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addInformalsList(informalsData: List<InformalsData>)

  @Query("SELECT * FROM $TABLE_INFORMALS")
  fun getInformals(): LiveData<List<InformalsData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addHospitality(hospitalityData: HospitalityData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addHospitalityList(hospitalityData: List<HospitalityData>)

  @Query("SELECT * FROM $TABLE_HOSPITALITY")
  fun getHospitality(): LiveData<List<HospitalityData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addGuestLectures(guestData: GuestData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addGuestLecturesList(guestData: List<GuestData>)

  @Query("SELECT * FROM $TABLE_GUEST_LECTURES")
  fun getGuestLectures(): LiveData<List<GuestData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addGallery(galleryData: GalleryData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addGalleryList(galleryData: List<GalleryData>)

  @Query("SELECT * FROM $TABLE_GALLERY")
  fun getGallery(): LiveData<List<GalleryData>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addClusters(clustersData: ClustersData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addClustersList(clustersData: List<ClustersData>)

  @Query("SELECT * FROM $TABLE_CLUSTERS")
  fun getClusterEvents(): LiveData<List<ClustersData>>

  @Query("SELECT DISTINCT $KEY_NAME, $KEY_CLUSTER_ID FROM $TABLE_CLUSTERS")
  fun getClusterNames(): LiveData<List<ClusterName>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addSchedule(scheduleData: ScheduleData)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addScheduleList(scheduleData: List<ScheduleData>)

  @Query("SELECT * FROM $TABLE_SCHEDULE")
  fun getSchedule(): LiveData<List<ScheduleData>>
}
