package edu.nitt.delta.core.model.user

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.BaseResponse

data class RegisterResponse(
  @SerializedName("user_id")
  val userId: Int
) : BaseResponse<String>() {
  override fun equals(response: Any?): Boolean {
    require(response is RegisterResponse)
    return super.equals(response) &&
      this.userId == response.userId
  }
}
