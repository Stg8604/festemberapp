package edu.nitt.deltaapp.core.profile

import edu.nitt.deltaapp.core.model.user.UserData

sealed class ProfileAction {

    object GetQrBitmap : ProfileAction()
    object GetQr : ProfileAction()
    object GetSubscribedEvents : ProfileAction()
    object GetUserDetails : ProfileAction()
    object GetScoreboard : ProfileAction()
    object GetCollegeDetails : ProfileAction()
    data class LoginUser(val email: String, val password: String) : ProfileAction()
    data class RegisterUser(val userData: Map<String, String?>) : ProfileAction()
    data class UpdateUserDetails(val userData: UserData) : ProfileAction()
    data class DAuthLoginUser(val authCode: String) : ProfileAction()
}
