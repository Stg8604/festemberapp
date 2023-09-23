package edu.nitt.delta.core.api

object Routes {
  const val LOGIN = "/auth/app"
  const val REGISTER = "/user/register"
  const val CHAT_MSG = "/chat/getmessage"
  const val CHAT_UNREAD_MSG = "/getmessage"
  const val CHAT_NEW_MSG = "/chat/newmessage"
  const val GET_EVENTS = "/events/details"
  const val EVENTS_SUBSCRIBE = "/events/register"
  const val EVENTS_UNSUBSCRIBE = "/events/unregister"
  const val GET_SUBSCRIBED_EVENTS = "/user/events/details"
  const val FCM_REGISTER = "/user/fcm/add"
  const val USER_DETAILS = "/user/details"
  const val USER_UPDATE_DETAILS = "/user/app/update"
  const val NITT_QR = "/tshirt/qr"
  const val NON_NITT_QR = "/pr/qr"
  const val SCOREBOARD = "/scoreboard"

  // Routes for Payload
  const val GUEST_LECTURES = "/payload/api/guestLectures"
  const val HOSPITALITY = "/payload/api/hospitality"
  const val INFORMALS = "/payload/api/informals"
  const val SPONSORS = "/payload/api/sponsors"
  const val ABOUTUS = "/payload/api/aboutus"
  const val WORKSHOPS = "/payload/api/workshops"
  const val CLUSTERS = "/payload/api/events"
  const val GALLERY = "/payload/api/gallery"
}
