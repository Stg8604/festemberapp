package edu.nitt.deltaapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.model.user.CollegeData
import edu.nitt.deltaapp.core.profile.ProfileAction
import edu.nitt.deltaapp.core.profile.ProfileViewModel
import edu.nitt.deltaapp.databinding.FragmentSignUp2Binding
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.showSnackbar_red

class SignUp2Fragment : Fragment() {
  private var binding by viewLifecycle<FragmentSignUp2Binding>()
  lateinit var profileViewModel: ProfileViewModel
  private lateinit var clg: List<CollegeData>
  private val TAG = "Signup2"
  private var colleges: MutableList<String> = ArrayList()
  val degrees = arrayOf("BTech", "MTech", "BArch", "BA", "BBA", "MBA", "MBBS", "PhD", "BSc", "MSc", "MCA", "Others")
  val years = arrayOf("1st Year", "2nd Year", "3rd Year", "4th Year", "5th Year", "Others")
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    profileViewModel.doAction(ProfileAction.GetCollegeDetails)
    observeProfileViewModel()
    binding = FragmentSignUp2Binding.inflate(inflater, container, false)
    updateDetails()
    binding.degree.setAdapter(
      ArrayAdapter(requireContext(), R.layout.drop_downlist,
        R.id.txtcollege, degrees)
    )
    binding.year.setAdapter(
      ArrayAdapter(requireContext(), R.layout.drop_downlist,
        R.id.txtcollege, years)
    )
    observeProfileViewModel()
    return binding.root
  }
  override fun onAttach(context: Context) {
    super.onAttach(context)
    val factory = (activity?.application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.topBarBinding.Logout.visibility = View.INVISIBLE
    binding.topBarBinding.Login.visibility = View.VISIBLE
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(SignUp2FragmentDirections.actionSignup2FragmentToLoginFragment())
    }
    binding.next2.setOnClickListener {
      if (validatedetails()) {
        findNavController().navigate(R.id.action_signUp2Fragment_to_signup3Fragment)
      }
    }
    binding.back2.setOnClickListener {
      findNavController().popBackStack()
    }
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(R.id.action_signUp2Fragment_to_navBarFragment)
    }
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(R.id.action_signup2Fragment_to_LoginFragment)
    }
  }
  private fun observeProfileViewModel() {
    profileViewModel.colleges.observe(viewLifecycleOwner,
      Observer {
        clg = profileViewModel.colleges.value ?: listOf(CollegeData(1, "No Colleges Found"))
        clg.forEach {
          val name = it.collegeName
          colleges.add(name)
        }
        binding.college.setAdapter(ArrayAdapter(requireContext(), R.layout.drop_downlist, R.id.txtcollege, colleges))
        Log.v(TAG, "hello " + clg.toString())
        Log.v(TAG, "College Fetched Successfully")
      })

    profileViewModel.error.observe(viewLifecycleOwner,
      Observer {
        String
        clg = listOf()
        Log.v(TAG, "College Fetch error")
        showSnackbar_red(it)
      })
  }
  private fun validatedetails(): Boolean {
    if (binding.degree.text.isEmpty()) {
      showSnackbar_red("Enter Degree")
      return false
    } else if (binding.year.text.isEmpty()) {
      showSnackbar_red("Enter Year")
      return false
    } else if (binding.address.text.isEmpty()) {
      showSnackbar_red("Enter Address")
      return false
    } else if (binding.college.text.isEmpty()) {
      showSnackbar_red("Enter College")
      return false
    } else if (!(colleges.contains(binding.college.text.toString()))) {
      showSnackbar_red("Select colleges from options")
      return false
    } else if (!(degrees.contains(binding.degree.text.toString()))) {
      showSnackbar_red("Select degrees from the list")
      return false
    } else if (!(years.contains(binding.year.text.toString()))) {
      showSnackbar_red("Select years from list")
      return false
    }
    SignUpFragment.registerData.degree = binding.degree.text.toString()
    SignUpFragment.registerData.year = binding.year.text.toString()
    SignUpFragment.registerData.address = binding.address.text.toString()
    SignUpFragment.registerData.college = binding.college.text.toString()
    return true
  }
  private fun updateDetails() {
    binding.degree.setText(SignUpFragment.registerData.degree)
    binding.year.setText(SignUpFragment.registerData.year)
    binding.address.setText(SignUpFragment.registerData.address)
    binding.college.setText(SignUpFragment.registerData.college)
  }
  companion object {
    const val TAG = "SIGNUP"
  }
}
