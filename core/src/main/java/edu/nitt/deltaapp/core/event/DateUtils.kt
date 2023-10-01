package edu.nitt.deltaapp.core.event

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import java.util.Calendar
import edu.nitt.deltaapp.core.DAY_0

const val TIME_FORMAT = "HH:mm:ss"
const val TIME_FORMAT_DISPLAY = "h:mm aa"
const val DATE_FORMAT = "yyyy-MM-dd"
const val DATE_FORMAT_DISPLAY = "EEE, MMM dd "
const val LANGUAGE = "en"
const val COUNTRY = "IN"

val sdfDate = SimpleDateFormat(DATE_FORMAT, Locale(LANGUAGE, COUNTRY))
val sdfTime = SimpleDateFormat(TIME_FORMAT, Locale(LANGUAGE, COUNTRY))

val sdfDisplayDate = SimpleDateFormat(DATE_FORMAT_DISPLAY, Locale(LANGUAGE, COUNTRY))
val sdfDisplayTime = SimpleDateFormat(TIME_FORMAT_DISPLAY, Locale(LANGUAGE, COUNTRY))

@Throws(ParseException::class)
fun String.getDateFromTimeString() = sdfTime.parse(this)

@Throws(ParseException::class)
fun String.getDateFromDateString() = sdfDate.parse(this)

fun Date.formatDate() = sdfDate.format(this)
fun Date.formatTime() = sdfTime.format(this)

fun Date.formatDateDisplay() = sdfDisplayDate.format(this)
fun Date.formatTimeDisplay() = sdfDisplayTime.format(this)

@Throws(ParseException::class)
fun String.formatTimeToDisplay(): String = this.getDateFromTimeString().formatTimeDisplay()

@Throws(ParseException::class)
fun String.formatDateToDisplay(): String = this.getDateFromDateString().formatDateDisplay()

fun Int.getDateforDay(): Date {
  require(this in 0..3)
  val date = DAY_0.getDateFromDateString()
  val calendar = Calendar.getInstance()
  calendar.time = date
  calendar.add(Calendar.DAY_OF_MONTH, this)
  return calendar.time
}

operator fun Date.plusAssign(time: Date) {
  val timeToAdd = Calendar.getInstance()
  timeToAdd.time = time

  this.time += (timeToAdd[Calendar.HOUR_OF_DAY] * 3600000)
  this.time += (timeToAdd[Calendar.MINUTE] * 60000)
  this.time += (timeToAdd[Calendar.SECOND] * 1000)
  this.time += timeToAdd[Calendar.MILLISECOND]
}
