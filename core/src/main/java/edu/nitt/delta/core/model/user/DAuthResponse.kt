package edu.nitt.delta.core.model.user

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.BaseResponse

class DAuthResponse(
  @SerializedName("user_id")
  val userID: Int,

  @SerializedName("user_details")
  val userDetails: DAuthUserData
) : BaseResponse<String>()
