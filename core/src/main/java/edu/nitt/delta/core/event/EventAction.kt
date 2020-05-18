package edu.nitt.delta.core.event

sealed class EventAction {

  object UpdateEvents : EventAction()
  data class GetEventsFiltered(val filter: EventFilter) : EventAction()
  data class Subscribe(val eventId: Long) : EventAction()
  data class Unsubscribe(val eventId: Long) : EventAction()
  object getSubscribedEvents : EventAction()
}
