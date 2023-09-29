package edu.nitt.delta.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.R
import edu.nitt.delta.databinding.FragmentSignUp3Binding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar_red

class SignUp3Fragment : Fragment() {
  private var binding by viewLifecycle<FragmentSignUp3Binding>()
  private var imgrsc: String = ""
  private var imgint: Int = 1
  private val mutableMap = mutableMapOf(
    1 to getUrl(R.drawable.fire_scaled).toString(),
    2 to getUrl(R.drawable.earth).toString(),
    3 to getUrl(R.drawable.water).toString(),
    4 to getUrl(R.drawable.wind).toString(),
    5 to getUrl(R.drawable.space).toString()
  )
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentSignUp3Binding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.topBarBinding.Logout.visibility = View.INVISIBLE
    binding.topBarBinding.Login.visibility = View.VISIBLE
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(SignUp3FragmentDirections.actionSignup3fragmentToLoginFragment())
    }
    binding.fire.setOnClickListener {
      if (!validatedetails()) {
        binding.fire.setImageResource(R.drawable.fire_scaled2)
        imgrsc = getUrl(R.drawable.fire_scaled).toString()
        binding.fire.setSelected(!binding.fire.isSelected)
      } else if (binding.fire.isSelected) {
        binding.fire.setImageResource(R.drawable.fire_scaled)
        binding.fire.setSelected(!binding.fire.isSelected)
        imgrsc = ""
      }
    }

    binding.earth.setOnClickListener {
      if (!validatedetails()) {
        binding.earth.setImageResource(R.drawable.earth2)
        binding.earth.setSelected(!binding.earth.isSelected)
        imgrsc = getUrl(R.drawable.earth).toString()
      } else if ((binding.earth.isSelected)) {
        binding.earth.setImageResource(R.drawable.earth)
        binding.earth.setSelected(!binding.earth.isSelected)
        imgrsc = ""
      }
    }
    binding.water.setOnClickListener {
      if (!validatedetails()) {
        binding.water.setImageResource(R.drawable.water2)
        imgrsc = getUrl(R.drawable.water).toString()
        binding.water.setSelected(!binding.water.isSelected)
      } else if ((binding.water.isSelected)) {
        binding.water.setImageResource(R.drawable.water)
        binding.water.setSelected(!binding.water.isSelected)
        imgrsc = ""
      }
    }
    binding.wind.setOnClickListener {
      if (!validatedetails()) {
        binding.wind.setImageResource(R.drawable.wind2)
        binding.wind.setSelected(!binding.wind.isSelected)
        imgrsc = getUrl(R.drawable.wind).toString()
      } else if ((binding.wind.isSelected)) {
        binding.wind.setImageResource(R.drawable.wind)
        binding.wind.setSelected(!binding.wind.isSelected)
        imgrsc = ""
      }
    }
    binding.space.setOnClickListener {
      if (!validatedetails()) {
        binding.space.setImageResource(R.drawable.space2)
        binding.space.setSelected(!binding.space.isSelected)
        imgrsc = getUrl(R.drawable.space).toString()
      } else if ((binding.space.isSelected)) {
        binding.space.setImageResource(R.drawable.space)
        binding.space.setSelected(!binding.space.isSelected)
        imgrsc = ""
      }
    }
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(R.id.action_signup3fragment_to_navBarFragment)
    }
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(R.id.action_signup3fragment_to_LoginFragment)
    }
    binding.next3.setOnClickListener {
      if (validatedetails()) {
        imgint = getKeyByValue(mutableMap, imgrsc)
        SignUpFragment.registerData.avatar = imgint
        findNavController().navigate(R.id.action_signUp3Fragment_to_signup4Fragment)
      } else {
        showSnackbar_red("Select Avatar")
      }
    }
    binding.back3.setOnClickListener {
      findNavController().popBackStack()
    }
  }
  private fun validatedetails(): Boolean {
    if (binding.fire.isSelected || binding.earth.isSelected || binding.water.isSelected || binding.wind.isSelected || binding.space.isSelected) {
      return true
    }
    return false
  }
  fun getUrl(res: Int): Uri? {
    return Uri.parse("android.resource://edu.edu.nitt.delta.app/$res")
  }
}
fun getKeyByValue(map: MutableMap<Int, String>, targetValue: String): Int {
  for ((key, value) in map) {
    if (value == targetValue) {
      return key
    }
  }
  return 2
}
