package edu.nitt.delta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.databinding.FragmentNavBarBinding
import edu.nitt.delta.helpers.viewLifecycle

class NavBarFragment : Fragment() {

  private var binding by viewLifecycle<FragmentNavBarBinding>()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = FragmentNavBarBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.backButton.setOnClickListener {
      findNavController().popBackStack()
    }
    binding.homeButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToHomeFragment())
    }
    binding.contactUsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToContactUsFragment())
    }
    binding.eventsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToEventsFragment())
    }
    binding.sponsorsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToSponsorsFragment())
    }
    binding.profileButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToDashboardFragment())
    }
  }
}
