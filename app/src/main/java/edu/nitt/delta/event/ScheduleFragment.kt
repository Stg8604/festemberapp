package edu.nitt.delta.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.delta.R
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.core.model.payload.Schedule.ScheduleData
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.databinding.FragmentScheduleBinding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar_green
import kotlinx.android.synthetic.main.fragment_schedule.day_0_indicator_bar
import kotlinx.android.synthetic.main.fragment_schedule.navBarButtonBinding
import kotlinx.android.synthetic.main.fragment_schedule.progressBar_schedule
import kotlinx.android.synthetic.main.fragment_schedule.progress_view
import kotlinx.android.synthetic.main.fragment_schedule.timeline_tv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScheduleFragment : Fragment() {
  private var binder by viewLifecycle<FragmentScheduleBinding>()
  private lateinit var viewmodel: EventViewModel
  private lateinit var sharedprefHelper: SharedPrefHelper
  private val adapter = ScheduleListRecyclerViewAdapter()
  private var eventsOnDay0 = mutableListOf<ScheduleData>()
  private var eventsOnDay1 = mutableListOf<ScheduleData>()
  private var eventsOnDay2 = mutableListOf<ScheduleData>()
  private var eventsOnDay3 = mutableListOf<ScheduleData>()
  private var animationDone: Boolean = false
  private var TAG = "ScheduleFragment"
  private var dataFetched = false
  private val fetchDataScope = CoroutineScope(Dispatchers.Main)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binder = FragmentScheduleBinding.inflate(inflater, container, false)
    return binder.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    sharedprefHelper =
      (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
    if (sharedprefHelper.isLoggedIn) {
      binder.topBarBinding.Logout.visibility = View.VISIBLE
      binder.topBarBinding.Login.visibility = View.INVISIBLE
    } else {
      binder.topBarBinding.Logout.visibility = View.INVISIBLE
      binder.topBarBinding.Login.visibility = View.VISIBLE
    }
    if (animationDone) {
      timeline_tv.visibility = View.VISIBLE
    }
    if (!dataFetched) {
      progress_view.visibility = View.VISIBLE
      progressBar_schedule.visibility = View.VISIBLE
      day_0_indicator_bar.visibility = View.INVISIBLE
    }
    navBarButtonBinding.setOnClickListener {
      if (dataFetched) {
        try {
          findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToNavBarFragment())
        } catch (e: IllegalArgumentException) {
          Log.d(TAG, "onViewCreated: Caught a IllegalArgumentException ${e.message}")
        }
      }
    }
      val factory =
        (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
      viewmodel = ViewModelProvider(requireActivity(), factory)[EventViewModel::class.java]
      viewmodel.doAction(EventAction.GetSchedule)
      viewmodel.schedule.observe(
        viewLifecycleOwner
      ) {
        binder.day0IndicatorBar.animate()
          .translationY(binder.day3.y + (binder.daysLinearLayout.height / 8).toFloat() - binder.day0IndicatorBar.height / 2)
        if (!dataFetched) {
          timeline_tv.visibility = View.INVISIBLE
          fetchDataScope.launch {
            DataFetcher.fetchData(eventsOnDay0, eventsOnDay1, eventsOnDay2, eventsOnDay3, it)
            updateUI()
          }
        }
        adapter.submitList(eventsOnDay3)
        initOnClickListeners()
        initEventListView()
      }
      binder.topBarBinding.Logout.setOnClickListener {
        if (it.isVisible) {
          sharedprefHelper.isLoggedIn = false
          it.visibility = View.INVISIBLE
          binder.topBarBinding.Login.visibility = View.VISIBLE
          sharedprefHelper.clear()
          findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToLoginFragment())
          showSnackbar_green("Logged out Successfully")
        }
      }
      binder.topBarBinding.Login.setOnClickListener {
        if (it.isVisible) {
          findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToLoginFragment())
        }
      }
    }
    private fun fetchEventOnDay(day: Int) {
      require(day in 0..3)
      when (day) {
        1 -> { adapter.submitList(eventsOnDay1) }
        2 -> { adapter.submitList(eventsOnDay2) }
        3 -> { adapter.submitList(eventsOnDay3) }
        0 -> { adapter.submitList(eventsOnDay0) }
      }
    }

  private fun initEventListView() {
    binder.eventList.layoutManager = LinearLayoutManager(context)
    binder.eventList.adapter = adapter
    adapter.selectedItem.observe(viewLifecycleOwner) {
      if (it != null) {
//         uncomment for enabling event item click
//          findNavController().navigate(ScheduleFragmentDirections.actionScheduleFragmentToEventDetailsFragment(it))
        adapter.selectedItem.value = null
      }
    }
  }
  private fun initOnClickListeners() {
    binder.day0.setOnClickListener {
      if (dataFetched) {
          binder.day0IndicatorBar.animate().interpolator = AccelerateDecelerateInterpolator()
          binder.day0IndicatorBar.animate().duration = 300
          binder.day0IndicatorBar.animate()
            .translationY(it.y + (binder.daysLinearLayout.height / 8).toFloat() - binder.day0IndicatorBar.height / 2)
          binder.day0.setTextColor(ContextCompat.getColor(requireContext(), R.color.cherryRed))
          binder.day1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          fetchEventOnDay(0)
        }
      }
      binder.day1.setOnClickListener {
        if (dataFetched) {
          binder.day0IndicatorBar.y
          binder.day0IndicatorBar.animate().interpolator = AccelerateDecelerateInterpolator()
          binder.day0IndicatorBar.animate().duration = 300
          binder.day0IndicatorBar.animate()
            .translationY(it.y + (binder.daysLinearLayout.height / 8).toFloat() - binder.day0IndicatorBar.height / 2)
          binder.day0.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day1.setTextColor(ContextCompat.getColor(requireContext(), R.color.cherryRed))
          binder.day2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          fetchEventOnDay(1)
        }
      }
      binder.day2.setOnClickListener {
        if (dataFetched) {
          binder.day0IndicatorBar.animate().interpolator = AccelerateDecelerateInterpolator()
          binder.day0IndicatorBar.animate().duration = 300
          binder.day0IndicatorBar.animate()
            .translationY(it.y + (binder.daysLinearLayout.height / 8).toFloat() - binder.day0IndicatorBar.height / 2)
          binder.day0.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day2.setTextColor(ContextCompat.getColor(requireContext(), R.color.cherryRed))
          binder.day3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          fetchEventOnDay(2)
        }
      }
      binder.day3.setOnClickListener {
        if (dataFetched) {
          binder.day0IndicatorBar.animate().interpolator = AccelerateDecelerateInterpolator()
          binder.day0IndicatorBar.animate().duration = 300
          binder.day0IndicatorBar.animate()
            .translationY(it.y + (binder.daysLinearLayout.height / 8).toFloat() - binder.day0IndicatorBar.height / 2)
          binder.day0.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
          binder.day3.setTextColor(ContextCompat.getColor(requireContext(), R.color.cherryRed))
          fetchEventOnDay(3)
        }
      }
  }

  object DataFetcher {
    // Function to calculate the nth Fibonacci number on a background thread
    suspend fun fetchData(
      eventsOnDay0: MutableList<ScheduleData>,
      eventsOnDay1: MutableList<ScheduleData>,
      eventsOnDay2: MutableList<ScheduleData>,
      eventsOnDay3: MutableList<ScheduleData>,
      it: List<ScheduleData>
    ) {
      return withContext(Dispatchers.Default) {
        fetchingData(eventsOnDay0, eventsOnDay1, eventsOnDay2, eventsOnDay3, it)
      }
    }

    private fun fetchingData(
      eventsOnDay0: MutableList<ScheduleData>,
      eventsOnDay1: MutableList<ScheduleData>,
      eventsOnDay2: MutableList<ScheduleData>,
      eventsOnDay3: MutableList<ScheduleData>,
      it: List<ScheduleData>
    ) {
      for (event in it) {
        Log.d("scheduleFragment", "event day: ${event.day} || event time: ${event.time} || event venue: ${event.venue}")
        when (event.day.toInt()) {
          0 -> { eventsOnDay0.add(event) }
          1 -> { eventsOnDay1.add(event) }
          2 -> { eventsOnDay2.add(event) }
          3 -> { eventsOnDay3.add(event) }
        }
      }
      quickSort(eventsOnDay0, 0, eventsOnDay0.size - 1)
      quickSort(eventsOnDay1, 0, eventsOnDay1.size - 1)
      quickSort(eventsOnDay2, 0, eventsOnDay2.size - 1)
      quickSort(eventsOnDay3, 0, eventsOnDay3.size - 1)
    }
    private fun quickSort(arr: MutableList<ScheduleData>, low: Int, high: Int) {
      if (low < high) {
        val pivotIndex = partition(arr, low, high)
        quickSort(arr, low, pivotIndex - 1)
        quickSort(arr, pivotIndex + 1, high)
      }
    }
    private fun partition(arr: MutableList<ScheduleData>, low: Int, high: Int): Int {
      val pivot = arr[high]
      var i = low - 1

      for (j in low until high) {
        if (checkTimings(arr[j].time, pivot.time)) {
          i++
          swap(arr, i, j)
        }
      }
      swap(arr, i + 1, high)
      return i + 1
    }
    private fun swap(arr: MutableList<ScheduleData>, i: Int, j: Int) {
      val temp = arr[i]
      arr[i] = arr[j]
      arr[j] = temp
    }

    private fun checkTimings(time: String, endtime: String): Boolean {
      if (!time.isNullOrEmpty() && !endtime.isNullOrEmpty()) {
      val pattern = "yyy-MM-dd'T'HH:mm:ss.SSS'Z'"
      val sdf = SimpleDateFormat(pattern, Locale.US)
      try {
        val date1: Date? = sdf.parse(time)
        val date2: Date? = sdf.parse(endtime)
        if (date1 != null) {
          return date1.before(date2)
        }
      } catch (e: ParseException) {
        e.printStackTrace()
      }
      return false
    } else {
      return false
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    fetchDataScope.cancel()
  }

  private fun updateUI() {
    try {
      progress_view.visibility = View.INVISIBLE
      progressBar_schedule.visibility = View.INVISIBLE
      day_0_indicator_bar.visibility = View.VISIBLE
    } catch (e: NullPointerException) {
      Log.d(TAG, "updateUI: Caught a NullPointerException: ${e.message}")
    }
    adapter.submitList(eventsOnDay3)
    dataFetched = true
    if (!animationDone) {
      timeline_tv.visibility = View.VISIBLE
      timeline_tv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.timeline_sponsors_in))
      animationDone = true
    }
  }
}
