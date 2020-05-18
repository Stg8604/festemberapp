package edu.nitt.delta.core.api

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
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface FestApiInterface {

  /** Login */
  @FormUrlEncoded
  @POST(Routes.LOGIN)
  suspend fun authenticate(
    @Field("user_email") email: String,
    @Field("user_pass") password: String
  ): LoginResponse

  /** Get All Chat messages of a user */
  @FormUrlEncoded
  @POST(Routes.CHAT_MSG)
  suspend fun getAllChatMessages(
    @Field("user_id") userId: Int,
    @Field("user_token") token: String
  ): ChatData

  /** Get unread Chat messages */
  @FormUrlEncoded
  @POST(Routes.CHAT_UNREAD_MSG)
  suspend fun getUnreadMessages(
    @Field("user_id") userId: Int,
    @Field("user_token") token: String
  ): ChatData

  /** Get all events */
  @GET(Routes.GET_EVENTS)
  suspend fun getEventsData(): EventDataResponse

  /** Register User (For non NITT Students) */
  @FormUrlEncoded
  @POST(Routes.REGISTER)
  suspend fun register(@FieldMap parameters: Map<String, String>): RegisterResponse

  /** Register for Particular event */
  @FormUrlEncoded
  @POST(Routes.EVENTS_SUBSCRIBE)
  suspend fun subscribeEvent(
    @Field("user_id") userID: Long,
    @Field("event_id") eventID: Long,
    @Field("user_token") token: String
  ): EventRegisterResponse

  /**
   * Send the FCM Token to the server
   *
   * NOTE: Send after every login
   *
   */
  @FormUrlEncoded
  @POST(Routes.FCM_REGISTER)
  suspend fun registerFcm(
    @Field("user_id") userId: Int,
    @Field("fcm_id") fcmToken: String,
    @Field("user_token") token: String
  ): FCMRegisterResponse

  /** Send a Chat Message */
  @FormUrlEncoded
  @POST(Routes.CHAT_NEW_MSG)
  suspend fun sendChatMessage(
    @Field("user_id") userId: Int,
    @Field("user_token") token: String,
    @Field("message") message: String
  ): ChatMessageSentResponse

  /** Unregister from Event */
  @FormUrlEncoded
  @POST(Routes.EVENTS_UNSUBSCRIBE)
  suspend fun unSubscribeEvent(
    @Field("user_id") userID: Long,
    @Field("event_id") eventID: Long,
    @Field("user_token") token: String
  ): EventRegisterResponse

  /** Get Events registered by user */
  @FormUrlEncoded
  @POST(Routes.GET_SUBSCRIBED_EVENTS)
  suspend fun getSubscribedEvents(
    @Field("user_id") userId: Long,
    @Field("user_token") token: String
  ): RegisteredEventsResponse

  /** Get QR code of user encoded in Base64 */
  @FormUrlEncoded
  @POST
  suspend fun getQr(
    @Field("user_id") userId: Int,
    @Field("user_token") token: String,
    @Url url: String
  ): QrResponse

  /** Get User details */
  @GET(Routes.USER_DETAILS)
  suspend fun getUserDetails(
    @Query("user_id") id: Int,
    @Query("user_token") token: String
  ): UserDetailsResponse

  /** Update User details */
  @FormUrlEncoded
  @POST(Routes.USER_UPDATE_DETAILS)
  suspend fun updateDetails(
    @Field("user_id") id: Int,
    @Field("user_token") token: String,
    @FieldMap parameters: Map<String, String>
  ): UpdateDetailsResponse
}
