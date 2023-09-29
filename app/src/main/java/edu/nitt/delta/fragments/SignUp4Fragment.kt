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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import edu.nitt.delta.R
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.profile.ProfileAction
import edu.nitt.delta.core.profile.ProfileViewModel
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.databinding.FragmentSignUp4Binding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar
class SignUp4Fragment : Fragment() {
  private var binding by viewLifecycle<FragmentSignUp4Binding>()
  private val TAG = "Signup4"
  lateinit var profileViewModel: ProfileViewModel
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentSignUp4Binding.inflate(inflater, container, false)
    updateDetails()
    return binding.root
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)

    val factory = (activity?.application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.submit.setOnClickListener {
      if (validatedetails()) {
        recaptcha()
      }
      observeProfileViewModel()
      }
    binding.back4.setOnClickListener {
      findNavController().popBackStack()
    }
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(R.id.action_signup4fragment_to_navBarFragment)
    }
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(R.id.action_signup4fragment_to_LoginFragment)
    }
  }
  private fun observeProfileViewModel() {
    profileViewModel.success.observe(viewLifecycleOwner,
      Observer {
        Log.v(TAG, "RegisterSuccess")
        showSnackbar("Registered Successfully")
        findNavController().navigate(R.id.action_signUp4Fragment_to_loginFragment)
      })
    profileViewModel.error.observe(viewLifecycleOwner,
      Observer {
        Log.v(TAG, "RegisterFailure")
        Log.v(TAG, it)

        showSnackbar("OOPS!! $it")
      })
  }
  private fun recaptcha() {
    SafetyNet.getClient(requireContext()).verifyWithRecaptcha("6LdCHV8oAAAAANuosxz57k3_qkofVBa7nG9WEW1j")
      .addOnSuccessListener(requireActivity(), OnSuccessListener { response ->
        val userResponseToken = response.tokenResult
        if (response.tokenResult?.isNotEmpty() == true) {
          Log.d(TAG, response.tokenResult.toString())
          SignUpFragment.registerData.recaptchaCode = response.tokenResult
          registerUser()
        }
      })
      .addOnFailureListener(requireActivity(), OnFailureListener { e ->
        if (e is ApiException) {
          Log.d(TAG, "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
        } else {
          Log.d(TAG, "Error: ${e.message}")
        }
      })
  }
  private fun registerUser() {
    profileViewModel.doAction(ProfileAction.RegisterUser(SignUpFragment.registerData.toMap()))
  }
  private fun validatedetails(): Boolean {
    if (binding.password.text.toString().length <8) {
      showSnackbar("Password Should Contain at least 8 characters")
      return false
    } else if ((containsNoNumbers(binding.password.text.toString())) || !(containsUpperAndLowerCase(binding.password.text.toString())) || !(containsSpecialCharacters(binding.password.text.toString()))) {
      showSnackbar("Follow password convention")
      return false
    } else if (binding.password.text.isEmpty()) {
      showSnackbar("Enter Valid Password")
      return false
    } else if (!(binding.password.text.toString().equals(binding.confpassword.text.toString()))) {
      showSnackbar("Password and confirm doesn't match")
      return false
    }
    SignUpFragment.registerData.password = binding.password.text.toString()
    SignUpFragment.registerData.is_app = 1
    SignUpFragment.registerData.sex = "Male"
    SignUpFragment.registerData.nationality = "Indian"
    SignUpFragment.registerData.pincode = "600092"
    SignUpFragment.registerData.state = "Tamil Nadu"
    SignUpFragment.registerData.city = "Chennai"
    SignUpFragment.registerData.voucherName = "None"
    SignUpFragment.registerData.sponsor = "None"
    SignUpFragment.registerData.referralCode = "None"
    return true
  }
  private fun updateDetails() {
    binding.password.setText(SignUpFragment.registerData.password)
    binding.confpassword.setText(SignUpFragment.registerData.password)
  }
  fun containsNoNumbers(input: String): Boolean {
    for (char in input) {
      if (char.isDigit()) {
        return false
      }
    }
    return true
  }
}
fun containsUpperAndLowerCase(input: String): Boolean {
  var containsUpper = false
  var containsLower = false
  for (char in input) {
    if (char.isUpperCase()) {
      containsUpper = true
    } else if (char.isLowerCase()) {
      containsLower = true
    }
    if (containsUpper && containsLower) {
      return true
    }
  }
  return false
}
fun containsSpecialCharacters(input: String): Boolean {
  val pattern = Regex("[^A-Za-z0-9 ]")
  return pattern.containsMatchIn(input)
}
