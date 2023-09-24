package edu.nitt.delta.core.profile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import edu.nitt.delta.core.FEST_DIR
import edu.nitt.delta.core.FEST_QR_SUFFIX
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.model.Result
import edu.nitt.delta.core.model.user.CollegeData
import edu.nitt.delta.core.model.user.CollegeResponse
import edu.nitt.delta.core.model.user.ScoreboardData
import edu.nitt.delta.core.model.user.UserData
import edu.nitt.delta.core.storage.SharedPrefHelper
import java.io.File
import javax.inject.Inject

class ProfileRepository @Inject constructor(
  private val festApi: FestApiInterface
) {

    val TAG = "ProfileRepository"

    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    // Gets all user details as in UserData.kt
    suspend fun getUserDetails(id: Long, token: String): Result<UserData> = try {
        val response = festApi.getUserDetails(id, token)
        Log.v(TAG, "user ${response.message!!}")
        if (response.statusCode == 200 && response.message != null) {
            Result.build { response.message!! }
        } else {
            Log.e(
                TAG,
                "request returned with status ${response.statusCode} and message : ${response.message}"
            )
            Result.build<UserData> { throw Exception("Error Fetching User Details") }
        }
    } catch (e: Exception) {
        Result.build<UserData> { throw e }
    }
  // Get QR code
  suspend fun getQr(userId: Int, token: String): Result<String> = try {
    val response = festApi.getQr(userId, token)
    Log.v(TAG, "checkQr " + response.message!!)
    if (response.statusCode == 200 && response.message != null) {
      Result.build { response.message!! }
    } else {
      Log.e(
        TAG,
        "request returned with status ${response.statusCode} and message : ${response.message}"
      )
      Result.build<String> { throw Exception("Error Fetching Qr code") }
    }
  } catch (e: Exception) {
    Result.build<String> { throw e }
  }

    // Updates user's details
    suspend fun updateDetails(id: Long, token: String, userData: UserData): Result<String> = try {
        val response = festApi.updateDetails(id, token, userData.toMap())
        if (response.statusCode == 200 && response.message != null) {
            sharedPrefHelper.username = userData.userName
            Result.build { response.message!! }
        } else {
            Log.e(
                TAG,
                "request returned with status ${response.statusCode} and message : ${response.message}"
            )
            Result.build<String> { throw Exception("Error Updating User Details") }
        }
    } catch (e: Exception) {
        Result.build<String> { throw e }
    }

    // Gets QR code from local
    fun getQrBitmap(application: Application): Result<Bitmap> = try {
        val bitmap: Bitmap
        val bmOptions = BitmapFactory.Options()
        bitmap = BitmapFactory.decodeFile(getQRImageFile(application).absolutePath, bmOptions)
        Result.build { bitmap }
    } catch (e: java.lang.Exception) {
        Result.build<Bitmap> { throw e }
    }

    // Helper function to get the QR
    private fun getQRImageFile(application: Application): File {
        val path: String =
            application.applicationContext.getExternalFilesDir(null)!!.absolutePath + FEST_DIR
        var file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        val fileName: String = path + sharedPrefHelper.email + FEST_QR_SUFFIX
        file = File(fileName)
        return file
    }

  // Register function fo Non NITT students
  suspend fun registerUsers(userData: Map<String, String?>): Result<String> = try {
    val response = festApi.register(userData)
    if (response.statusCode == 200 && response.message != null) {
      Result.build { response.message!! }
    } else {
      Log.e(
        TAG,
        "request returned with status ${response.statusCode} and message : ${response.message}"
      )
      Result.build<String> { throw Exception("Error Registering User") }
    }
  } catch (e: Exception) {
    Result.build<String> { throw e }
  }

  // Login User
  suspend fun loginUser(email: String, password: String): Result<String> = try {
    val response = festApi.authenticate(email, password)
    if (response.statusCode == 200 && response.message != null) {
      sharedPrefHelper.token = response.message!!
      sharedPrefHelper.userId = response.userId.toLong()
      sharedPrefHelper.email = email
      sharedPrefHelper.isLoggedIn = true
      Result.build { response.message!! }
    } else {
      Log.e(
        TAG,
        "request returned with status ${response.statusCode} and message : ${response.message}"
      )
      Result.build<String> { throw Exception("Error Login User") }
    }
  } catch (e: Exception) {
    Log.v(TAG, "InCatch")
    Result.build<String> { throw e }
  }

  // Get the scoreboard
  suspend fun getScoreboard(): Result<List<ScoreboardData>> = try {
    val response = festApi.getScoreboard()
    Log.e("RESPONSE_IN", "Reached the call")
    if (response.statusCode == 200 && response.message != null) {
      Log.e("RESPONSE_API", response.statusCode.toString())
      Log.e("RESPONSE_API", response.message.toString())
      Result.build { response.message!! }
    } else {
      Log.e(
        TAG,
        "request returned with status ${response.statusCode} and message : ${response.message}"
      )
      Result.build<List<ScoreboardData>> { throw Exception("Error Fetching User Details") }
    }
  } catch (e: Exception) {
    Result.build<List<ScoreboardData>> { throw e }
  }

  // get college details
  suspend fun getCollegeDetails(): Result<List<CollegeData>> = try {
    val response: CollegeResponse = festApi.getCollegeDetails()
    Log.i(TAG, "getCollegeDetails: " + response.message)
    if (response.statusCode == 200 && response.message != null) {
      Result.build { response.message!! }
    } else {
      Result.build<List<CollegeData>> { throw Exception("Error occurred while Fetching College ") }
    }
  } catch (e: Exception) {
    Result.build<List<CollegeData>> { throw Exception("Error occurred while fetching colleges") }
  }
}
