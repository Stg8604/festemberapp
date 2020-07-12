package edu.nitt.delta.core.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.nitt.delta.core.model.Result
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventViewModel @Inject constructor(
  val eventRepository: EventRepository,
  val sharedPreferences: SharedPrefHelper
) : BaseViewModel<EventAction>() {

  val TAG = "EventViewModel"

  val events: LiveData<List<EventData>> = eventRepository.events

  val clusters: LiveData<List<String>> = eventRepository.clusters

  private val mutableRegisteredEvents = MutableLiveData<List<String>>()
  val registeredEvents: LiveData<List<String>>
    get() = mutableRegisteredEvents

  override fun doAction(action: EventAction): Any = when (action) {
    EventAction.UpdateEvents -> updateEvents()
    is EventAction.GetEventsFiltered -> getEventsFiltered(action.filter)
    is EventAction.Subscribe -> subscribeEvent(action.eventId)
    is EventAction.Unsubscribe -> unsubscribeEvent(action.eventId)
    EventAction.getSubscribedEvents -> getSubscribedEvents()
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
}
