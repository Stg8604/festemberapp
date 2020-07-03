package edu.nitt.delta.core.profile

import edu.nitt.delta.core.model.user.UserData

sealed class ProfileAction {

    object GetQrBitmap : ProfileAction()
    object GetSubscribedEvents : ProfileAction()
    object GetUserDetails : ProfileAction()
    data class UpdateUserDetails(val userData: UserData) : ProfileAction()
}
