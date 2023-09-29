package edu.nitt.delta.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.R
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.databinding.FragmentSponsorsBinding
import edu.nitt.delta.event.SponsorsAdapter
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar_green
import kotlinx.android.synthetic.main.fragment_schedule.navBarButtonBinding
import kotlinx.android.synthetic.main.fragment_sponsors.sponsors

class SponsorsFragment : Fragment() {

  private lateinit var viewModel: EventViewModel
  private var binding by viewLifecycle<FragmentSponsorsBinding>()
  private lateinit var sharedPrefHelper: SharedPrefHelper

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSponsorsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    sharedPrefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (sharedPrefHelper.isLoggedIn) {
      binding.topBarBinding.Logout.visibility = View.VISIBLE
      binding.topBarBinding.Login.visibility = View.INVISIBLE
    } else {
      binding.topBarBinding.Logout.visibility = View.INVISIBLE
      binding.topBarBinding.Login.visibility = View.VISIBLE
    }
    val listView = view.findViewById<ListView>(R.id.ListView)
    sponsors.startAnimation(AnimationUtils.loadAnimation(context, R.anim.timeline_sponsors_in))
    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewModel = ViewModelProvider(requireActivity(), factory)[EventViewModel::class.java]
    viewModel.doAction(EventAction.GetSponsors)
    viewModel.sponsors.observe(viewLifecycleOwner
    ) {
      val listAdapter = SponsorsAdapter(view.context, it)
      listView.adapter = listAdapter
    }
    navBarButtonBinding.setOnClickListener {
      findNavController().navigate(SponsorsFragmentDirections.actionSponsorsFragmentToNavBarFragment())
    }
    binding.topBarBinding.Logout.setOnClickListener {
      if (it.isVisible) {
        sharedPrefHelper.isLoggedIn = false
        it.visibility = View.INVISIBLE
        binding.topBarBinding.Login.visibility = View.VISIBLE
        sharedPrefHelper.clear()
        findNavController().navigate(SponsorsFragmentDirections.actionSponsorsFragmentToLoginFragment())
        showSnackbar_green("Logged out Successfully")
      }
    }
    binding.topBarBinding.Login.setOnClickListener {
      if (it.isVisible) {
        findNavController().navigate(SponsorsFragmentDirections.actionSponsorsFragmentToLoginFragment())
      }
    }
  }
}
