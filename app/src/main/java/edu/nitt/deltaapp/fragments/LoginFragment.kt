package edu.nitt.deltaapp.fragments

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
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.model.user.UserData
import edu.nitt.deltaapp.core.profile.ProfileAction
import edu.nitt.deltaapp.core.profile.ProfileViewModel
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import edu.nitt.deltaapp.databinding.LoginFragmentBinding
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.showSnackbar_green
import edu.nitt.deltaapp.showSnackbar_red

class LoginFragment : Fragment() {

  private var binding by viewLifecycle<LoginFragmentBinding>()

  lateinit var profileViewModel: ProfileViewModel
  lateinit var sharedprefHelper: SharedPrefHelper
  val TAG = "LoginFragment"
  private lateinit var user: UserData

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = LoginFragmentBinding.inflate(inflater, container, false)
//    SignUpFragment.registerData = RegisterUserData()
    binding.btnSignUp.setOnClickListener {
      findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }
    observeProfileViewModel()
//    observeProfileViewModelFcm()
    return binding.root
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val factory = (activity?.application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    sharedprefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.topBarBinding.Login.visibility = View.INVISIBLE
    binding.topBarBinding.Logout.visibility = View.INVISIBLE
    binding.btnLogin.setOnClickListener {
      login()
    }

    binding.btnDAuthLogin.setOnClickListener {
      findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDAuthFragment())
    }

    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavBarFragment())
    }
  }

//  private fun observeProfileViewModelFcm() {
// //    profileViewModel.fcmRegisterMessage.observe(viewLifecycleOwner,
// //      Observer {
// //        binding.btnLogin.isEnabled = true
// //        val intent = Intent(requireActivity(), MainActivity::class.java)
// //        startActivity(intent)
// //        requireActivity().finish()
// //      })
//    profileViewModel.userData.observe(viewLifecycleOwner,
//      Observer {
//        user = profileViewModel.userData.value!!
//        Log.v(TAG, "QR obtained" + user.toString())
//        showSnackbar("YAY!! $it")
//      })
//    profileViewModel.error.observe(viewLifecycleOwner,
//      Observer {
//        sharedprefHelper.clear()
//        binding.btnLogin.isEnabled = true
//        showSnackbar(it)
//      })
//  }

  private fun observeProfileViewModel() {
    profileViewModel.success.observe(viewLifecycleOwner,
      Observer {
        Log.v(TAG, "LoginSuccess")
//        profileViewModel.doAction(ProfileAction.RegisterFCM)
        showSnackbar_green(it)
        sharedprefHelper.isLoggedIn = true
        println("asdfasdfasdfasfd" + sharedprefHelper.USER_DETAILS)
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
      })

    profileViewModel.error.observe(viewLifecycleOwner,
      Observer {
        binding.btnLogin.isEnabled = true
        Log.v(TAG, "LoginFailure")
        showSnackbar_red(it)
      })
  }

  private fun login() {
    val email = binding.loginEdit.text.toString()
    val password = binding.loginPasswordEdit.text.toString()
    if (isValidCredentials(email, password)) {
      Log.v(TAG, "LoginCalled")
      binding.btnLogin.isEnabled = false
      profileViewModel.doAction(ProfileAction.LoginUser(email, password))
    } else {
      showSnackbar_red("Invalid User Credentials")
    }
  }

  private fun isValidCredentials(email: String, password: String): Boolean {
    return true
  }
}
