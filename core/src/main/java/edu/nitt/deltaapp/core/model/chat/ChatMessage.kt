package edu.nitt.deltaapp.core.model.chat

import com.google.gson.annotations.SerializedName

data class ChatMessage(
  @SerializedName("message")
  var message: String,

  @SerializedName("sender")
  val sender: String,

  @SerializedName("timestamp")
  val timeStamp: String
) {
  override fun equals(message: Any?): Boolean {
    require(message is ChatMessage)
    return this.message == message.message &&
      this.sender == message.sender &&
      this.timeStamp == message.timeStamp
  }
}
