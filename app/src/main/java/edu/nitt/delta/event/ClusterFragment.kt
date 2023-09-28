package edu.nitt.delta.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.R
import edu.nitt.delta.adapters.ClusterAdapter
import edu.nitt.delta.databinding.FragmentClusterListBinding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.models.ClusterDetails
import kotlinx.android.synthetic.main.fragment_schedule.navBarButtonBinding

class ClusterFragment : Fragment(), ClusterAdapter.OnItemClickListener {
  private var binding by viewLifecycle<FragmentClusterListBinding>()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentClusterListBinding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val clusterData = listOf(
      ClusterDetails("ART", R.drawable.artn),
      ClusterDetails("MUSIC", R.drawable.musict),
      ClusterDetails("DANCE", R.drawable.dancen),
      ClusterDetails("FASHION", R.drawable.fashionn),
      ClusterDetails("DRAMATICS", R.drawable.dramaticsn),
      ClusterDetails("GAMING", R.drawable.gamingn),
      ClusterDetails("SHRUTILAYA", R.drawable.shrutin),
      ClusterDetails("ENGLISH LITS", R.drawable.eln),
      ClusterDetails("TAMIL LITS", R.drawable.tln),
      ClusterDetails("TELUGU LITS", R.drawable.tlln),
      ClusterDetails("HINDI LITS", R.drawable.hln),
      ClusterDetails("PHOTOGRAPHY", R.drawable.phton)
    )
    val gridView = view.findViewById<GridView>(R.id.gridView)
    val clusterAdapter = ClusterAdapter(requireContext(), clusterData) { clusterDetails ->
      onItemClick(clusterDetails) // Call your onItemClick function with the clicked item
    }
    gridView.adapter = clusterAdapter
    navBarButtonBinding.setOnClickListener {
      findNavController().navigate(ClusterFragmentDirections.actionEventsFragmentToNavBarFragment())
    }
  }
  override fun onItemClick(clusterDetails: ClusterDetails) {
    when (clusterDetails.name) {
      "ART" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "art"))
      }
      "MUSIC" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "music"))
      }
      "DANCE" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "dance"))
      }
      "FASHION" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "fashion"))
      }
      "DRAMATICS" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "dramatics"))
      }
      "ENGLISH LITS" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "english lits"))
      }
      "HINDI LITS" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "hindi lits"))
      }
      "TAMIL LITS" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "tamil lits"))
      }
      "TELUGU LITS" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "telugu lits"))
      }
      "GAMING" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "gaming"))
      }
      "SHRUTILAYA" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "shrutilaya"))
      }
      "PHOTOGRAPHY" -> {
        findNavController().navigate(
          ClusterFragmentDirections.actionEventsFragmentToEventListFragment(
            "photography"))
      }
    }
  }
}
