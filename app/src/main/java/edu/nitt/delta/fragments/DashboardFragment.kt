package edu.nitt.delta.fragments
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.R
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.api.FestApiModule.TAG
import edu.nitt.delta.core.model.user.UserData
import edu.nitt.delta.core.profile.ProfileAction
import edu.nitt.delta.core.profile.ProfileViewModel
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.databinding.DashboardFragmentBinding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar

class DashboardFragment : Fragment() {
  private lateinit var profileViewModel: ProfileViewModel
  private var binding by viewLifecycle<DashboardFragmentBinding>()
  private lateinit var user: UserData
  private lateinit var qr_hash: String
  private lateinit var sharedPrefHelper: SharedPrefHelper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DashboardFragmentBinding.inflate(inflater, container, false)
    if (sharedPrefHelper.username == "") {
      println(profileViewModel.doAction(ProfileAction.GetUserDetails))
      var userdat = profileViewModel.userData
      println(userdat)
    } else {
      binding.personname.text = "${sharedPrefHelper.username}"
      profileViewModel.doAction(ProfileAction.GetQrBitmap)
    }
    observeProfileViewModel()
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardfragment_to_navBarFragment)
    }
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardFragment_to_LoginFragment)
    }
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)
    sharedPrefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
    val factory = (activity?.application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
  }
  private fun observeProfileViewModel() {
    profileViewModel.userData.observe(viewLifecycleOwner,
      Observer {
        // needs to be edited to incorporate the latest changes
        user = profileViewModel.userData.value ?: UserData("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", R.drawable.avatar1, "", "", "")
        profileViewModel.doAction(ProfileAction.GetQr)
        profileViewModel.doAction(ProfileAction.GetQrBitmap)
        sharedPrefHelper.username = user.userName
        binding.personname.text = "${sharedPrefHelper.username}"
        binding.clgname.text = user.college
        binding.degreename.text = user.degree
        binding.yearnumber.text = user.year
      })
    binding.qr.setImageBitmap(profileViewModel.qrBitmap)
    profileViewModel.error.observe(viewLifecycleOwner,
      Observer {
        Log.v(TAG, "Error Occurred")
        showSnackbar("OOPS!! Some Error occurred")
      })
  }
}
