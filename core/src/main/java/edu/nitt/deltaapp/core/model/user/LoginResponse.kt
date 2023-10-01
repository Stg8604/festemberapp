package edu.nitt.deltaapp.core.model.user

import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.BaseResponse

data class LoginResponse(
  @SerializedName("user_id")
  var userId: Int
) : BaseResponse<String>() {
  override fun equals(response: Any?): Boolean {
    require(response is LoginResponse)
    return super.equals(response) &&
      this.userId == response.userId
  }
}
