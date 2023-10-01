package edu.nitt.deltaapp.fragments

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.core.model.user.RegisterUserData
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import edu.nitt.deltaapp.databinding.FragmentSignUpBinding
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.showSnackbar_red

class SignUpFragment : Fragment() {
  private var binding by viewLifecycle<FragmentSignUpBinding>()
  lateinit var front_anim: AnimatorSet
  lateinit var back_anim: AnimatorSet
  private lateinit var sharedprefHelper: SharedPrefHelper
  companion object {
    var registerData: RegisterUserData = RegisterUserData()
  }
  var isFront = true
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentSignUpBinding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    updateDetails()
    binding.topBarBinding.Logout.visibility = View.INVISIBLE
    binding.topBarBinding.Login.visibility = View.INVISIBLE
    val front = binding.signupfront as androidx.constraintlayout.widget.ConstraintLayout
    val back = binding.signupback as androidx.constraintlayout.widget.ConstraintLayout
    var scale = this.resources.displayMetrics.density
    front.cameraDistance = 8000 * scale
    back.cameraDistance = 8000 * scale
    front_anim = AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
    back_anim = AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(R.id.action_signupfragment_to_navBarFragment)
    }
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
    }
    binding.didYouKno.setOnClickListener {
      if (isFront) {
        front_anim.setTarget(front)
        back_anim.setTarget(back)
        front_anim.start()
        back_anim.start()
        isFront = false
        binding.signupback.visibility = View.VISIBLE
      } else {
        front_anim.setTarget(back)
        back_anim.setTarget(front)
        back_anim.start()
        front_anim.start()
        isFront = true
        binding.signupfront.visibility = View.VISIBLE
        binding.signupback.visibility = View.GONE
      }
      binding.arrow3.setOnClickListener {
        front_anim.setTarget(back)
        back_anim.setTarget(front)
        back_anim.start()
        front_anim.start()
        isFront = true
        binding.signupfront.visibility = View.VISIBLE
      }
    }
    binding.next.setOnClickListener {
      if (validatedetails()) {
        findNavController().navigate(R.id.action_signUpFragment_to_signup2Fragment)
      }
    }
    binding.login.setOnClickListener {
      findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
    }
  }
  private fun validatedetails(): Boolean {
    if (binding.username.text.isEmpty()) {
      showSnackbar_red("Enter Valid Username")
      return false
    } else if (binding.name.text.isEmpty() || !containsNoNumbers(binding.name.text.toString())) {
      showSnackbar_red("Enter Valid Full Name")
      return false
    } else if (binding.ephno.text.isEmpty() || binding.ephno.text.toString().length> 12) {
      showSnackbar_red("Enter Valid Phone Number")
      return false
    } else if (binding.email.text.isEmpty()) {
      showSnackbar_red("Enter Valid Email")
      return false
    }
    registerData.userName = binding.username.text.toString()
    registerData.fullName = binding.name.text.toString()
    registerData.phoneNo = binding.ephno.text.toString()
    registerData.email = binding.email.text.toString()
    return true
  }
  private fun updateDetails() {
    binding.username.setText(registerData.userName)
    binding.name.setText(registerData.fullName)
    binding.ephno.setText(registerData.phoneNo)
    binding.email.setText(registerData.email)
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
