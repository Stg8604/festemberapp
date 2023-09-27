package edu.nitt.delta.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventFilter
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.databinding.FragmentScheduleBinding
import edu.nitt.delta.helpers.viewLifecycle

class ScheduleFragment : Fragment() {

  private var binder by viewLifecycle<FragmentScheduleBinding>()
  private lateinit var viewmodel: EventViewModel
  private val adapter = EventListRecyclerViewAdapter()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binder = FragmentScheduleBinding.inflate(inflater, container, false)
    return binder.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binder.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToLoginFragment())
    }
    val factory =
      (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory).get(EventViewModel::class.java)

    initOnClickListeners()
    initEventListView()
    fetchEventOnDay(0)
  }

  private fun fetchEventOnDay(day: Int) {
    require(day in 0..3)
    val filter = EventFilter.Builder()
      .setDay(day)
      .build()
    val events = viewmodel.doAction(EventAction.GetEventsFiltered(filter)) as LiveData<List<EventData>>
    events.observe(viewLifecycleOwner,
      Observer {
        adapter.submitList(it)
      })
  }

  private fun initEventListView() {
    binder.eventList.layoutManager = LinearLayoutManager(context)
    binder.eventList.adapter = adapter
    adapter.selectedItem.observe(viewLifecycleOwner,
      Observer {
        if (it != null) {
          findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToEventDetailsFragment(it))
          adapter.selectedItem.value = null
        }
      })
  }

  private fun initOnClickListeners() {
    binder.day0.setOnClickListener { fetchEventOnDay(0) }
    binder.day1.setOnClickListener { fetchEventOnDay(1) }
    binder.day2.setOnClickListener { fetchEventOnDay(2) }
    binder.day3.setOnClickListener { fetchEventOnDay(3) }
  }
}
