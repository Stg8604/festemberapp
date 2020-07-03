package edu.nitt.delta.core.profile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import edu.nitt.delta.core.FEST_DIR
import edu.nitt.delta.core.FEST_QR_SUFFIX
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.model.Result
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
        var bitmap: Bitmap
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
}
