package edu.nitt.delta.core.model

import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {
  @SerializedName("status_code")
  var statusCode: Int = 0

  @SerializedName("message")
  var message: T? = null

  override fun equals(response: Any?): Boolean {
    require(response is BaseResponse<*>)
    return this.statusCode == response.statusCode &&
      this.message == response.message
  }
}
