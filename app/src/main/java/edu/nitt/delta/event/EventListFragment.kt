package edu.nitt.delta.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventFilter
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.databinding.FragmentEventListBinding
import edu.nitt.delta.helpers.viewLifecycle

class EventListFragment : Fragment() {

  val TAG = "EventListFragment"

  private var binding by viewLifecycle<FragmentEventListBinding>()
  private lateinit var viewmodel: EventViewModel
  private val adapter = EventListRecyclerViewAdapter()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentEventListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory).get(EventViewModel::class.java)

    val cluster = navArgs<EventListFragmentArgs>().value.clusterName
    initEventObserver(cluster)
    initEventListView()
  }

  private fun initEventListView() {
    binding.eventList.layoutManager = LinearLayoutManager(context)
    binding.eventList.adapter = adapter
    adapter.selectedItem.removeObservers(viewLifecycleOwner)
    adapter.selectedItem.observe(viewLifecycleOwner,
      Observer {
        if (it != null) {
          Log.i(TAG, "event selected ${it.name}")
          findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToEventDetailsFragment(it))
          adapter.selectedItem.value = null
        }
      })
  }

  private fun initEventObserver(cluster: String) {
    val filter = EventFilter.Builder()
      .setClusters(cluster)
      .build()
    val eventsLiveData = viewmodel.doAction(EventAction.GetEventsFiltered(filter)) as LiveData<List<EventData>>
    eventsLiveData.observe(viewLifecycleOwner,
      Observer {
        adapter.submitList(it)
      })
  }
}
