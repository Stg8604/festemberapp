package edu.nitt.deltaapp.fragments
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.api.FestApiModule.TAG
import edu.nitt.deltaapp.core.model.user.UserData
import edu.nitt.deltaapp.core.profile.ProfileAction
import edu.nitt.deltaapp.core.profile.ProfileViewModel
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import edu.nitt.deltaapp.databinding.DashboardFragmentBinding
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.showSnackbar_green

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
    sharedPrefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
    binding = DashboardFragmentBinding.inflate(inflater, container, false)
    binding.topBarBinding.Logout.visibility = View.VISIBLE
    binding.topBarBinding.Logout.setOnClickListener {
      sharedPrefHelper.isLoggedIn = false
      sharedPrefHelper.clear()
      it.visibility = View.INVISIBLE
      findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
      showSnackbar_green("Logged out Successfully")
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(R.id.action_dashboardfragment_to_navBarFragment)
    }
    profileViewModel.doAction(ProfileAction.GetUserDetails)
    Log.d(TAG, "onViewCreated: ")
    val userdat = profileViewModel.userData
    userdat.observe(viewLifecycleOwner) {

      binding.personname.text = it.fullName
      Log.d(TAG, "fullname: ${it.fullName}")
      binding.clgname.text = it.college
      Log.d(TAG, "college: ${it.college}")
      binding.degreename.text = it.degree
      Log.d(TAG, "degree: ${it.degree}")
      binding.yearnumber.text = it.year
      Log.d(TAG, "year: ${it.year}")
      binding.rollno.text = it.userName
      Log.d(TAG, "username: ${it.userName}")
      if (it.loginMethod == "db") {
        binding.roll.text = "User Name"
      }
    }
    profileViewModel.doAction(ProfileAction.GetQr)
    profileViewModel.qr.observe(viewLifecycleOwner
    ) {
      qr_hash = profileViewModel.qr.value ?: ""
      sharedPrefHelper.qrImage = qr_hash
      val qr = returnBitmap(qr_hash)
      binding.qr.setImageBitmap(qr)
      Log.v(TAG, "QR Hash in profile fragment $qr_hash")
    }
    Log.d(TAG, "onCreateView: $userdat")
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)
    sharedPrefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
    val factory = (activity?.application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
  }
  private fun returnBitmap(qr_hash: String): Bitmap? {
    val decodedString: ByteArray = Base64.decode(qr_hash, Base64.DEFAULT)
    val bitmap_qr = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return bitmap_qr
  }
}
