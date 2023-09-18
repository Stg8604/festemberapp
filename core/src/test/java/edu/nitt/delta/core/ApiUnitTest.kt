package edu.nitt.delta.core

import android.app.Application
import com.google.gson.Gson
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.api.Routes
import edu.nitt.delta.core.model.FCM.FCMRegisterResponse
import edu.nitt.delta.core.model.chat.ChatData
import edu.nitt.delta.core.model.chat.ChatMessageSentResponse
import edu.nitt.delta.core.model.event.EventDataResponse
import edu.nitt.delta.core.model.event.EventRegisterResponse
import edu.nitt.delta.core.model.event.RegisteredEventsResponse
import edu.nitt.delta.core.model.user.LoginResponse
import edu.nitt.delta.core.model.user.QrResponse
import edu.nitt.delta.core.model.user.RegisterResponse
import edu.nitt.delta.core.model.user.UpdateDetailsResponse
import edu.nitt.delta.core.model.user.UserDetailsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.net.HttpURLConnection
import java.net.InetAddress
import kotlin.coroutines.CoroutineContext

class ApiUnitTest : CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = Job() + Dispatchers.Unconfined

  private lateinit var festApiInterface: FestApiInterface
  private lateinit var mockWebServer: MockWebServer
  private lateinit var gson: Gson

  private val mDispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
      println("request received at : ${request.path}")
      return when (request.path!!.split("?")[0]) { // split is done to ignore query params
        Routes.LOGIN -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(LOGIN_RES)
        Routes.NON_NITT_QR -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(QR_RES_1)
        Routes.NITT_QR -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(QR_RES_2)
        Routes.CHAT_MSG -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(CHAT_RES)
        Routes.CHAT_UNREAD_MSG -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(CHAT_RES)
        Routes.GET_EVENTS -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(EVENT_RES)
        Routes.REGISTER -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(REGISTER_RES)
        Routes.EVENTS_SUBSCRIBE -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(EVENT_REGISTER_RES)
        Routes.FCM_REGISTER -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(FCM_REGISTER_RES)
        Routes.CHAT_NEW_MSG -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(CHAT_NEW_MSG_RES)
        Routes.EVENTS_UNSUBSCRIBE -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(EVENT_UNREGISTER_RES)
        Routes.GET_SUBSCRIBED_EVENTS -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(REGISTERED_EVENTS_RES)
        Routes.USER_DETAILS -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(USER_DETAILS_RES)
        Routes.USER_UPDATE_DETAILS -> MockResponse()
          .setResponseCode(HttpURLConnection.HTTP_OK)
          .setBody(UPDATE_DETAILS_RES)
        else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
      }
    }
  }

  @Before
  @Throws(Exception::class)
  fun setup() {
    mockWebServer = MockWebServer()
    mockWebServer.dispatcher = mDispatcher
    mockWebServer.start(InetAddress.getLocalHost(), 4632)

    val appComponent = DaggerApplicationComponent.factory()
      .create(Mockito.mock(Application::class.java))

    festApiInterface = appComponent.getFestApi()
    gson = appComponent.getGson()
  }

  @After
  fun shutdown() {
    mockWebServer.shutdown()
  }

  @Test
  fun testAuth() = runBlocking {
    val res1 = festApiInterface.authenticate("email@nitt.edu", "password")
    val expectedResponse1 = gson.fromJson(LOGIN_RES, LoginResponse::class.java)
    assert(res1 == expectedResponse1)

    val res2 = festApiInterface.register(mapOf())
    val expectedResponse2 = gson.fromJson(REGISTER_RES, RegisterResponse::class.java)
    assert(res2 == expectedResponse2)
  }

  @Test
  fun testQr() = runBlocking {
    val res1 = festApiInterface.getQr(25, "hbfgklwqgdlkngklngtckgtukleg", Routes.NON_NITT_QR)
    val expectedResponse1 = gson.fromJson(QR_RES_1, QrResponse::class.java)
    assert(res1 == expectedResponse1)

    val res2 = festApiInterface.getQr(25, "hbfgklwqgdlkngklngtckgtukleg", Routes.NITT_QR)
    val expectedResponse2 = gson.fromJson(QR_RES_2, QrResponse::class.java)
    assert(res2 == expectedResponse2)
  }

  @Test
  fun testChat() = runBlocking {
    val res1 = festApiInterface.getAllChatMessages(25, "hbfgklwqgdlkngklngtckgtukleg")
    val expectedResponse = gson.fromJson(CHAT_RES, ChatData::class.java)
    assert(res1 == expectedResponse)

    val res2 = festApiInterface.getUnreadMessages(25, "hbfgklwqgdlkngklngtckgtukleg")
    assert(res2 == expectedResponse)

    val res3 = festApiInterface.sendChatMessage(25, "lvwraonherprfwugh", "how are you")
    val expectedResponse3 = gson.fromJson(CHAT_NEW_MSG_RES, ChatMessageSentResponse::class.java)
    assert(res3 == expectedResponse3)
  }

  @Test
  fun testUserDetails() = runBlocking {
    val res1 = festApiInterface.getUserDetails(25, "ewbgweriybwibrfewbgu")
    val expectedResponse1 = gson.fromJson(USER_DETAILS_RES, UserDetailsResponse::class.java)
    assert(res1 == expectedResponse1)

    val res2 = festApiInterface.updateDetails(25, "rtfuihreguhtonughtbghu", mapOf())
    val expectedResponse2 = gson.fromJson(UPDATE_DETAILS_RES, UpdateDetailsResponse::class.java)
    assert(res2 == expectedResponse2)
  }

  @Test
  fun testEvents() = runBlocking {
    val res1 = festApiInterface.getEventsData()
    val expectedResponse1 = gson.fromJson(EVENT_RES, EventDataResponse::class.java)
    assert(res1 == expectedResponse1)

    val res2 = festApiInterface.subscribeEvent(25, 177, "wrftukierwtuierwgtu")
    val expectedResponse2 = gson.fromJson(EVENT_REGISTER_RES, EventRegisterResponse::class.java)
    assert(res2 == expectedResponse2)

    val res3 = festApiInterface.unSubscribeEvent(25, 177, "wrftukierwtuierwgtu")
    val expectedResponse3 = gson.fromJson(EVENT_UNREGISTER_RES, EventRegisterResponse::class.java)
    assert(res3 == expectedResponse3)

    val res4 = festApiInterface.getSubscribedEvents(25, "qerdulewbuguebwfcuhewcf")
    val expectedResponse4 = gson.fromJson(REGISTERED_EVENTS_RES, RegisteredEventsResponse::class.java)
    assert(res4 == expectedResponse4)
  }

  @Test
  fun testFCMTokenSend() = runBlocking {
    val res = festApiInterface.registerFcm(25, "aeubhuwtwtgbur", "975cyn7932ycm485ypo432y")
    val expectedResponse = gson.fromJson(FCM_REGISTER_RES, FCMRegisterResponse::class.java)
    assert(res == expectedResponse)
  }
}
