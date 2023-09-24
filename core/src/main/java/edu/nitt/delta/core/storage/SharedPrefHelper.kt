package edu.nitt.delta.core.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefHelper @Inject constructor(context: Context) {

  val USER_DETAILS = "FestUserDetails"

  private val sharedPreferences: SharedPreferences

  object Key {
    val LOGGED_IN = "logged_in"
    val EMAIL = "email"
    val TOKEN = "token"
    val USERNAME = "username"
    val USERID = "user_id"
    val QR_STATUS = "qr_status"
    val QR_IMAGE = "qr_image"
    val SCHEDULE_INSTRUCTION_SHOW = "schedule_instruction_shown"
  }

  object Default {
    val LOGGED_IN = false
    val EMAIL = ""
    val TOKEN = ""
    val USERNAME = ""
    val USERID = 0.toLong()
    val QR_STATUS = false
    val QR_IMAGE = "QR_IMAGE"
    val SCHEDULE_INSTRUCTION_SHOW = false
  }

  init {
    sharedPreferences = context.getSharedPreferences(
      USER_DETAILS,
      Context.MODE_PRIVATE
    )
  }

  var isLoggedIn: Boolean
    get() = sharedPreferences.getBoolean(
      Key.LOGGED_IN,
      Default.LOGGED_IN
    )
    set(value) = sharedPreferences.edit().putBoolean(Key.LOGGED_IN, value).apply()

  var email: String?
    get() = sharedPreferences.getString(
      Key.EMAIL,
      Default.EMAIL
    )
    set(value) = sharedPreferences.edit().putString(Key.EMAIL, value).apply()

  var token: String
    get() = sharedPreferences.getString(
      Key.TOKEN,
      Default.TOKEN
    )!!
    set(value) = sharedPreferences.edit().putString(Key.TOKEN, value).apply()

  var username: String?
    get() = sharedPreferences.getString(
      Key.USERNAME,
      Default.USERNAME
    )
    set(value) = sharedPreferences.edit().putString(Key.USERNAME, value).apply()

  var userId: Long
    get() = sharedPreferences.getLong(
      Key.USERID,
      Default.USERID
    )
    set(value) = sharedPreferences.edit().putLong(Key.USERID, value).apply()

  var isQrDownloaded: Boolean
    get() = sharedPreferences.getBoolean(
      Key.QR_STATUS,
      Default.QR_STATUS
    )
    set(value) = sharedPreferences.edit().putBoolean(Key.QR_STATUS, value).apply()

  var scheduleInstructionsShown: Boolean
    get() = sharedPreferences.getBoolean(
      Key.SCHEDULE_INSTRUCTION_SHOW,
      Default.SCHEDULE_INSTRUCTION_SHOW
    )
    set(value) = sharedPreferences.edit().putBoolean(Key.SCHEDULE_INSTRUCTION_SHOW, value).apply()

  var qrImage: String
    get() = sharedPreferences.getString(Key.QR_IMAGE, Default.QR_IMAGE)!!
    set(value) = sharedPreferences.edit().putString(Key.QR_IMAGE, value).apply()
  // Clear all the preferences

  fun clear() {
    sharedPreferences.edit().clear().apply()
  }
}
