package edu.nitt.delta.core.profile

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.nitt.delta.core.event.EventRepository
import edu.nitt.delta.core.model.Result
import edu.nitt.delta.core.model.user.ScoreboardData
import edu.nitt.delta.core.model.user.UserData
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val application: Application) : BaseViewModel<ProfileAction>() {

    private val mutableRegisteredEvents = MutableLiveData<List<String>>()
    val registeredEvents: LiveData<List<String>>
        get() = mutableRegisteredEvents

    lateinit var userData: MutableLiveData<UserData>

    var qrBitmap: Bitmap? = null
    var scores: MutableLiveData<List<ScoreboardData>> = MutableLiveData<List<ScoreboardData>>(ArrayList())

  @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    @Inject
    lateinit var eventRepository: EventRepository

    @Inject
    lateinit var profileRepository: ProfileRepository

    override fun doAction(action: ProfileAction): Any = when (action) {
      is ProfileAction.UpdateUserDetails -> updateDetails(action.userData)
      is ProfileAction.GetSubscribedEvents -> getSubscribedEvents()
      is ProfileAction.GetUserDetails -> getUserDetails()
      is ProfileAction.GetQrBitmap -> getQrBitmap()
      is ProfileAction.GetScoreboard -> getScoreboard()
      is ProfileAction.LoginUser -> loginUser(action.email, action.password)
      is ProfileAction.RegisterUser -> registerUser(action.userData)
  }

    // Gets QR from local
    private fun getQrBitmap() {
        when (val res = profileRepository.getQrBitmap(application)) {
            is Result.Value -> qrBitmap = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

    // Gets all the registered events of a user from EventRepository
    private fun getSubscribedEvents() = launch {
        when (val res = eventRepository.getSubscribedEvents(sharedPrefHelper.userId, sharedPrefHelper.token)) {
            is Result.Value -> mutableRegisteredEvents.value = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

    // Gets the user details from ProfileRepository
    private fun getUserDetails() = launch {
        when (val res = profileRepository.getUserDetails(sharedPrefHelper.userId, sharedPrefHelper.token)) {
            is Result.Value -> userData.value = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

    // Updates the user details
    private fun updateDetails(userData: UserData) = launch {
        when (val res = profileRepository.updateDetails(sharedPrefHelper.userId, sharedPrefHelper.token, userData)) {
            is Result.Value -> mutableSuccess.value = "Successfully updated user details"
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

  // Register The User
  private fun registerUser(userData: Map<String, String?>) = launch {
    when (val res = profileRepository.registerUsers(userData)) {
      is Result.Value -> mutableSuccess.postValue("Successfully Registered User")
      is Result.Error -> mutableError.postValue(res.exception.message)
    }
  }

  // Login user
  private fun loginUser(email: String, password: String) = launch {
    when (val res = profileRepository.loginUser(email, password)) {
      is Result.Value -> mutableSuccess.postValue("Successfully Logged in User")
      is Result.Error -> mutableError.postValue("Unable to Login")
    }
  }

  // Get Scoreboard
  private fun getScoreboard() = launch {
    when (val res = profileRepository.getScoreboard()) {
      is Result.Value -> scores.postValue(res.value)
      is Result.Error -> mutableError.value = res.exception.message
    }
  }
}
