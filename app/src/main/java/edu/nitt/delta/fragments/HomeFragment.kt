package edu.nitt.delta.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.databinding.HomeFragmentBinding
import edu.nitt.delta.helpers.viewLifecycle

class HomeFragment : Fragment() {

  private var binding by viewLifecycle<HomeFragmentBinding>()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = HomeFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnLogout.setOnClickListener {
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    binding.btnEvents.setOnClickListener {
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEventsFragment())
    }

    binding.btnSchedule.setOnClickListener {
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToScheduleFragment())
    }
  }
}
