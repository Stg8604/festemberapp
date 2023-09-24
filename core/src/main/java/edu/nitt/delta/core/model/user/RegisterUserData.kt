package edu.nitt.delta.core.model.user

import com.google.gson.annotations.SerializedName

data class RegisterUserData(
  @SerializedName("user_name")
  var userName: String? = null,

  @SerializedName("user_email")
  var email: String? = null,

  @SerializedName("user_fullname")
  var fullName: String? = null,

  @SerializedName("user_password")
  var password: String? = null,

  @SerializedName("user_sex")
  var sex: String? = null,

  @SerializedName("user_nationality")
  var nationality: String? = null,

  @SerializedName("user_address")
  var address: String? = null,

  @SerializedName("user_pincode")
  var pincode: String? = null,

  @SerializedName("user_state")
  var state: String? = null,

  @SerializedName("user_phno")
  var phoneNo: String? = null,

  @SerializedName("user_degree")
  var degree: String? = null,

  @SerializedName("user_year")
  var year: String? = null,

  @SerializedName("user_college")
  var college: String? = null,

  @SerializedName("user_city")
  var city: String? = null,

  @SerializedName("user_voucher_name")
  var voucherName: String? = null,

  @SerializedName("user_sponsor")
  var sponsor: String? = null,

  @SerializedName("user_referral_code")
  var referralCode: String? = null,

  @SerializedName("recaptcha_code")
  var recaptchaCode: String? = null,

  @SerializedName("is_app")
  var is_app: Int = 1,

  @SerializedName("avatar")
  var avatar: String? = null
) {
  fun toMap(): Map<String, String?> =
    mapOf(
      "user_name" to userName,
      "user_email" to email,
      "user_fullname" to fullName,
      "user_password" to password,
      "user_sex" to sex,
      "user_nationality" to nationality,
      "user_address" to address,
      "user_pincode" to pincode,
      "user_state" to state,
      "user_phno" to phoneNo,
      "user_degree" to degree,
      "user_year" to year,
      "user_college" to college,
      "user_city" to city,
      "user_voucher_name" to voucherName,
      "user_sponsor" to sponsor,
      "user_referral_code" to referralCode,
      "recaptcha_code" to recaptchaCode,
      "avatar" to avatar,
      "is_app" to is_app.toString()
    )
}
