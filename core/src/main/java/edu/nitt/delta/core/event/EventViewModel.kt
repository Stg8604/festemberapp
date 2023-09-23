package edu.nitt.delta.core.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventViewModel @Inject constructor(
  private val eventRepository: EventRepository,
  private val sharedPreferences: SharedPrefHelper
) : BaseViewModel<EventAction>() {

  val TAG = "EventViewModel"

  val events: LiveData<List<EventData>> = eventRepository.events

  val clusters: LiveData<List<String>> = eventRepository.clusters

  val guestLectures = MutableLiveData<List<GuestData>>()
  val hospitality = MutableLiveData<List<HospitalityData>>()
  val informals = MutableLiveData<List<InformalsData>>()
  val sponsors = MutableLiveData<List<SponsorsData>>()
  val aboutUs = MutableLiveData<List<AboutUsData>>()
  val workshops = MutableLiveData<List<WorkshopData>>()
  val clusterEvents = MutableLiveData<List<ClustersData>>()
  val gallery = MutableLiveData<List<GalleryData>>()

  private val mutableRegisteredEvents = MutableLiveData<List<String>>()
  val registeredEvents: LiveData<List<String>>
    get() = mutableRegisteredEvents

  override fun doAction(action: EventAction): Any = when (action) {
    EventAction.UpdateEvents -> updateEvents()
    is EventAction.GetEventsFiltered -> getEventsFiltered(action.filter)
    is EventAction.Subscribe -> subscribeEvent(action.eventId)
    is EventAction.Unsubscribe -> unsubscribeEvent(action.eventId)
    EventAction.getSubscribedEvents -> getSubscribedEvents()
    is EventAction.GetGuestLectures -> getGuestLectures()
    is EventAction.GetHospitality -> getHospitality()
    is EventAction.GetInformals -> getInformals()
    is EventAction.GetSponsors -> getSponsors()
    is EventAction.GetAboutUs -> getAboutUs()
    is EventAction.GetWorkshops -> getWorkshop()
    is EventAction.GetClusters -> getClusters()
    is EventAction.GetGallery -> getGallery()
  }

  private fun getSubscribedEvents() = launch {
    when (val res = eventRepository.getSubscribedEvents(sharedPreferences.userId, sharedPreferences.token)) {
      is Result.Value -> mutableRegisteredEvents.value = res.value
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun unsubscribeEvent(eventId: Long) = launch {
    when (val res = eventRepository.unsubscribeEvent(sharedPreferences.userId, eventId, sharedPreferences.token)) {
      is Result.Value -> mutableSuccess.postValue("Successfully subscribed to event")
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun subscribeEvent(eventId: Long) = launch {
    when (val res = eventRepository.subscribeEvent(sharedPreferences.userId, eventId, sharedPreferences.token)) {
      is Result.Value -> mutableSuccess.postValue("Successfully unsubscribed to event")
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getEventsFiltered(eventFilter: EventFilter): LiveData<List<EventData>> = eventFilter.filter(events)

  private fun updateEvents() = launch {
    when (val res = eventRepository.getEvents()) {
      is Result.Value -> mutableSuccess.postValue("Successfully fetched events")
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getGuestLectures() = launch {
    when (val res = eventRepository.getGuestLectures()) {
      is Result.Value -> {
        guestLectures.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched guest lectures")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getHospitality() = launch {
    when (val res = eventRepository.getHospitality()) {
      is Result.Value -> {
        hospitality.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched Hospitality Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getInformals() = launch {
    when (val res = eventRepository.getInformals()) {
      is Result.Value -> {
        informals.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched Informals Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getSponsors() = launch {
    when (val res = eventRepository.getSponsors()) {
      is Result.Value -> {
        sponsors.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched Sponsors Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getAboutUs() = launch {
    when (val res = eventRepository.getAboutUs()) {
      is Result.Value -> {
        aboutUs.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched AboutUs Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getWorkshop() = launch {
    when (val res = eventRepository.getWorkshops()) {
      is Result.Value -> {
        workshops.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched Workshop Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getClusters() = launch {
    when (val res = eventRepository.getClusters()) {
      is Result.Value -> {
        clusterEvents.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched Cluster & Event Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  private fun getGallery() = launch {
    when (val res = eventRepository.getGallery()) {
      is Result.Value -> {
        gallery.postValue(res.value)
        mutableSuccess.postValue("Successfully fetched Gallery Details")
      }
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }
}
