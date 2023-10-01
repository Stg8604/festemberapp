package edu.nitt.deltaapp.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SharedPrefTest {

  lateinit var pref: SharedPrefHelper

  object InputSet {
    val logged_in = true
    val email = "theInitalEmail@delta.com"
    val token = "yhf287f23ufbuef7fnhf9hfof1091fijfie"
    val username = "inital_user_name"
    val userid = 5665.toLong()
    val qr_status = true
    val schedule_instruction_shown = true
  }

  @Before
  fun setup() {
    val testContext = getInstrumentation().context
    pref = SharedPrefHelper(testContext)

    pref.isLoggedIn = InputSet.logged_in
    pref.email = InputSet.email
    pref.token = InputSet.token
    pref.username = InputSet.username
    pref.userId = InputSet.userid
    pref.isQrDownloaded = InputSet.qr_status
    pref.scheduleInstructionsShown = InputSet.schedule_instruction_shown
  }

  @Test
  fun test_SharedPref_GetMethods() {
    Assert.assertEquals(pref.isLoggedIn, InputSet.logged_in)
    Assert.assertEquals(pref.email, InputSet.email)
    Assert.assertEquals(pref.token, InputSet.token)
    Assert.assertEquals(pref.username, InputSet.username)
    Assert.assertEquals(pref.userId, InputSet.userid)
    Assert.assertEquals(pref.isQrDownloaded, InputSet.qr_status)
    Assert.assertEquals(pref.scheduleInstructionsShown, InputSet.schedule_instruction_shown)
  }

  @Test
  fun test_SharedPref_Clear() {
    pref.clear()

    Assert.assertEquals(pref.isLoggedIn, SharedPrefHelper.Default.LOGGED_IN)
    Assert.assertEquals(pref.email, SharedPrefHelper.Default.EMAIL)
    Assert.assertEquals(pref.token, SharedPrefHelper.Default.TOKEN)
    Assert.assertEquals(pref.username, SharedPrefHelper.Default.USERNAME)
    Assert.assertEquals(pref.userId, SharedPrefHelper.Default.USERID)
    Assert.assertEquals(pref.isQrDownloaded, SharedPrefHelper.Default.QR_STATUS)
    Assert.assertEquals(
      pref.scheduleInstructionsShown,
      SharedPrefHelper.Default.SCHEDULE_INSTRUCTION_SHOW
    )
  }
}
