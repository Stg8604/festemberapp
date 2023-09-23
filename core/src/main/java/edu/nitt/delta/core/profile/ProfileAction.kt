package edu.nitt.delta.core.profile

import edu.nitt.delta.core.model.user.UserData

sealed class ProfileAction {

    object GetQrBitmap : ProfileAction()
    object GetSubscribedEvents : ProfileAction()
    object GetUserDetails : ProfileAction()
    object GetScoreboard : ProfileAction()
    data class LoginUser(val email: String, val password: String) : ProfileAction()
    data class RegisterUser(val userData: Map<String, String?>) : ProfileAction()
    data class UpdateUserDetails(val userData: UserData) : ProfileAction()
}
