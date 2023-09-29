package edu.nitt.delta.core.model.user

import com.google.gson.annotations.SerializedName

data class UserData(
  @SerializedName("user_name")
  val userName: String,

  @SerializedName("user_email")
  val email: String,

  @SerializedName("sex")
  val sex: String,

  @SerializedName("college")
  val college: String,

  @SerializedName("othercollege")
  val otherCollege: String,

  @SerializedName("degree")
  val degree: String,

  @SerializedName("yearOfStudy")
  val yearOfStudy: String,

  @SerializedName("branch")
  val branch: String,

  @SerializedName("phno")
  val phoneNo: String,

  @SerializedName("nationality")
  val nationality: String,

  @SerializedName("address")
  val address: String,

  @SerializedName("city")
  val city: String,

  @SerializedName("state")
  val state: String,

  @SerializedName("voucher_name")
  var voucherName: String,

  @SerializedName("pincode")
  val pincode: String,

  @SerializedName("sponsor")
  val sponsor: String,

  @SerializedName("referral_code")
  val referralCode: String,

  @SerializedName("acco")
  val accommodation: String,

  @SerializedName("avatar")
  val avatar: Int,

  @SerializedName("user_loginmethod")
  val loginMethod: String,

  @SerializedName("user_fullname")
  val fullName: String,

  @SerializedName("year")
  val year: String
) {
  override fun equals(user: Any?): Boolean {
    require(user is UserData)
    return this.userName == user.userName &&
      this.sex == user.sex &&
      this.college == user.college &&
      this.otherCollege == user.otherCollege &&
      this.degree == user.degree &&
      this.year == user.year &&
      this.branch == user.branch &&
      this.phoneNo == user.phoneNo &&
      this.nationality == user.nationality &&
      this.address == user.address &&
      this.city == user.city &&
      this.state == user.state &&
      this.voucherName == user.voucherName &&
      this.pincode == user.pincode &&
      this.sponsor == user.sponsor &&
      this.referralCode == user.referralCode
  }

  fun toMap(): Map<String, String> =
    mapOf(
      "user_name" to userName,
      "user_degree" to degree,
      "user_year" to year,
      "user_phno" to phoneNo,
      "user_college" to college,
      "user_city" to city,
      "user_state" to state,
      "user_address" to address,
      "user_voucher_name" to voucherName
    )
}
