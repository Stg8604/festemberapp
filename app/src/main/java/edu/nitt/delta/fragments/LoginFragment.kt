package edu.nitt.delta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.R
import edu.nitt.delta.databinding.LoginFragmentBinding
import edu.nitt.delta.helpers.viewLifecycle

class LoginFragment : Fragment() {

  private var binding by viewLifecycle<LoginFragmentBinding>()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = LoginFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnLogin.setOnClickListener {
      findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
  }
}
