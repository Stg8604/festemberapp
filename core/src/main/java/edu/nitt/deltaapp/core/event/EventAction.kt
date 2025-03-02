package edu.nitt.deltaapp.core.event

sealed class EventAction {

  object UpdateEvents : EventAction()
  data class GetEventsFiltered(val clusterID: Int) : EventAction()
  data class Subscribe(val eventId: Long) : EventAction()
  data class Unsubscribe(val eventId: Long) : EventAction()
  object getSubscribedEvents : EventAction()
  object GetGuestLectures : EventAction()
  object GetHospitality : EventAction()
  object GetInformals : EventAction()
  object GetSponsors : EventAction()
  object GetAboutUs : EventAction()
  object GetWorkshops : EventAction()
  object GetClusters : EventAction()
  object GetGallery : EventAction()
  object GetSchedule : EventAction()
}
