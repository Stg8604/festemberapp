package edu.nitt.delta.core.api

object Routes {
  const val BASE_URL = "https://api.festember.com"
  const val LOGIN = "/auth/app"
  const val REGISTER = "/user/register"
  const val CHAT_MSG = "/chat/getmessage"
  const val CHAT_UNREAD_MSG = "/getmessage"
  const val CHAT_NEW_MSG = "/chat/newmessage"
  const val GET_EVENTS = "/events/details"
  const val EVENTS_REGISTER = "/events/register"
  const val EVENTS_UNREGISTER = "/events/unregister"
  const val REGISTERED_EVENTS = "/user/events/details"
  const val FCM_REGISTER = "/user/fcm/add"
  const val USER_DETAILS = "/user/details"
  const val USER_UPDATE_DETAILS = "/user/app/update"
  const val NITT_QR = "/tshirt/qr"
  const val NON_NITT_QR = "/pr/qr"
}
