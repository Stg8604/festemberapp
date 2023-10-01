package edu.nitt.deltaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import edu.nitt.deltaapp.databinding.FragmentNavBarBinding
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.models.CarouselItemTypeEnum
import edu.nitt.deltaapp.models.ClustersNameEnum

class NavBarFragment : Fragment() {

  private var binding by viewLifecycle<FragmentNavBarBinding>()
  private lateinit var sharedprefHelper: SharedPrefHelper
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
    sharedprefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()

    binding.backButton.setOnClickListener {
      findNavController().popBackStack()
    }
    binding.homeButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToHomeFragment())
    }
    binding.contactUsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToContactUsFragment())
    }
    binding.sponsorsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToSponsorsFragment())
    }
    binding.profileButton.setOnClickListener {
      if (sharedprefHelper.isLoggedIn) {
        findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToDashboardFragment())
      } else {
        findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToLoginFragment())
      }
    }
    binding.eventsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToClusterFragment())
    }
    binding.guestLecturesButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToEventListFragment(ClustersNameEnum.WorkshopGLInformal, CarouselItemTypeEnum.GL))
    }
    binding.workshopsButton.setOnClickListener {
      findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToEventListFragment(ClustersNameEnum.WorkshopGLInformal, CarouselItemTypeEnum.Workshop))
    }
    binding.informalsButton.setOnClickListener {
      findNavController().navigate(
        NavBarFragmentDirections.actionNavBarFragmentToEventListFragment(
          ClustersNameEnum.WorkshopGLInformal,
          CarouselItemTypeEnum.Informal
        )
      )
      binding.leaderBoardButton.setOnClickListener {
        findNavController().navigate(NavBarFragmentDirections.actionNavBarFragmentToLeaderboardFragment())
      }
    }
  }
}
