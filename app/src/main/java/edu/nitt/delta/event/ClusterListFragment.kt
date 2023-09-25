package edu.nitt.delta.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.databinding.FragmentClusterListBinding
import edu.nitt.delta.helpers.viewLifecycle

class ClusterListFragment : Fragment() {

  val TAG = "ClusterListFragment"

  private var binding by viewLifecycle<FragmentClusterListBinding>()
  private lateinit var viewmodel: EventViewModel
  private val adapter = ClusterListRecyclerViewAdapter()

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

    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory).get(EventViewModel::class.java)

    viewmodel.doAction(EventAction.UpdateEvents)

    initClusterObserver()
    initClusterListView()

    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(ClusterListFragmentDirections.actionEventsFragmentToNavBarFragment())
    }
  }

  private fun initClusterListView() {
    binding.clusterList.layoutManager = LinearLayoutManager(context)
    binding.clusterList.adapter = adapter
    adapter.selectedItem.removeObservers(viewLifecycleOwner)
    adapter.selectedItem.observe(viewLifecycleOwner,
      Observer {
        if (it.isNotBlank()) {
          findNavController().navigate(ClusterListFragmentDirections.actionEventsFragmentToEventListFragment(it))
          adapter.selectedItem.value = ""
        }
      })
  }

  private fun initClusterObserver() {
    viewmodel.clusters.removeObservers(viewLifecycleOwner)
    viewmodel.clusters.observe(viewLifecycleOwner,
      Observer {
        adapter.submitList(it)
        if (it.isNotEmpty())
          Log.i(TAG, it.reduce { acc, s -> "$acc $s" })
      })
  }
}
