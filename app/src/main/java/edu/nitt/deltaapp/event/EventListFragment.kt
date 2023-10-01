package edu.nitt.deltaapp.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.event.EventAction
import edu.nitt.deltaapp.core.event.EventViewModel
import edu.nitt.deltaapp.core.model.payload.Clusters.ClusterName
import edu.nitt.deltaapp.core.model.payload.Clusters.EventDetail
import edu.nitt.deltaapp.core.model.payload.Clusters.Prize
import edu.nitt.deltaapp.databinding.FragmentEventListBinding
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.models.CarouselItemTypeEnum
import edu.nitt.deltaapp.models.ClustersNameEnum

class EventListFragment : Fragment() {

  val TAG = "EventListFragment"

  private var binding by viewLifecycle<FragmentEventListBinding>()
  private lateinit var viewmodel: EventViewModel
  private val adapter = EventListViewPagerAdapter()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentEventListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory)[EventViewModel::class.java]
    val clusterID = getClusterID(navArgs<EventListFragmentArgs>().value.clusterEnumID)

    val itemType = navArgs<EventListFragmentArgs>().value.itemType

    when (itemType) {
      CarouselItemTypeEnum.Workshop -> {
        viewmodel.doAction(EventAction.GetWorkshops)
        binding.clusterNameText.text = "Workshops"
        binding.rightArrow.visibility = View.INVISIBLE
        binding.leftArrow.visibility = View.INVISIBLE
      }
      CarouselItemTypeEnum.GL -> {
        viewmodel.doAction(EventAction.GetGuestLectures)
        binding.clusterNameText.text = "Guest Lectures"
        binding.rightArrow.visibility = View.INVISIBLE
        binding.leftArrow.visibility = View.INVISIBLE
      }
      CarouselItemTypeEnum.Informal -> {
        viewmodel.doAction(EventAction.GetInformals)
        binding.clusterNameText.text = "Informals"
        binding.rightArrow.visibility = View.INVISIBLE
        binding.leftArrow.visibility = View.INVISIBLE
      }
      CarouselItemTypeEnum.Event -> {
        viewmodel.currentClusterName.postValue(ClusterName(getClusterName(clusterID), clusterID))
      }
    }

    initEventObserver(clusterID, itemType = itemType)
    initEventListView(itemType)
    initOnClickListeners()
  }

  private fun initEventListView(itemType: CarouselItemTypeEnum) {
    binding.viewPager.adapter = adapter
    adapter.selectedItem.removeObservers(viewLifecycleOwner)
    adapter.selectedItem.observe(viewLifecycleOwner,
      Observer {
        if (it != null) {
          Log.i(TAG, "event selected ${it.name}")
          findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToEventDetailsFragment(it, itemType))
          adapter.selectedItem.value = null
        }
      })
  }

  private fun initEventObserver(clusterID: Int, itemType: CarouselItemTypeEnum) {
    when (itemType) {
      CarouselItemTypeEnum.GL -> {
        viewmodel.guestLectures.observe(viewLifecycleOwner) {
          val gLList = it.map {
            EventDetail(
              image = it.image,
              name = it.name,
              description = it.description,
              day = it.day.toLong(),
              venue = it.venue,
              time = it.time,
              registrationLink = it.registrationLink,
              contact = it.contact,
              id = it.id,
              prize = listOf<Prize>(),
              ruleBook = "",
              rawFormat = "",
              rawJudgingCriteria = ""
            )
          }
          adapter.submitList(gLList)
        }
      }

      CarouselItemTypeEnum.Informal -> {
        viewmodel.informals.observe(viewLifecycleOwner) {
          val informalsList = it.map {
            EventDetail(
              image = it.image,
              name = it.name,
              description = it.description,
              day = it.day.toLong(),
              venue = it.venue,
              time = it.time,
              registrationLink = it.registrationLink,
              contact = it.contact,
              id = it.id,
              prize = listOf<Prize>(),
              ruleBook = "",
              rawFormat = "",
              rawJudgingCriteria = ""
            )
          }
          adapter.submitList(informalsList)
        }
      }

      CarouselItemTypeEnum.Workshop -> {
        viewmodel.workshops.observe(viewLifecycleOwner) {
          val workshopsList = it.map {
            EventDetail(
              image = it.image,
              name = it.name,
              description = it.description,
              day = it.day.toLong(),
              venue = it.venue,
              time = it.time,
              registrationLink = it.registrationLink,
              contact = it.contact,
              id = it.id,
              prize = listOf<Prize>(),
              ruleBook = "",
              rawFormat = "",
              rawJudgingCriteria = ""
            )
          }
          adapter.submitList(workshopsList)
        }
      }

      CarouselItemTypeEnum.Event -> {
        viewmodel.currentClusterName.removeObservers(viewLifecycleOwner)
        viewmodel.currentClusterName.observe(viewLifecycleOwner) { currentCluster ->
          binding.clusterNameText.text = currentCluster.name
          viewmodel.eventsLiveData.postValue(viewmodel.clusterEvents.value?.find { it.clusterID == currentCluster.clusterID }?.eventDetails)
        }
        viewmodel.clusterEvents.observe(viewLifecycleOwner) {
          viewmodel.eventsLiveData.postValue(viewmodel.clusterEvents.value?.find { it.clusterID == viewmodel.currentClusterName.value?.clusterID }?.eventDetails)
        }
        viewmodel.eventsLiveData.observe(viewLifecycleOwner) {
          adapter.submitList(it)
        }
      }
    }
  }

  private fun initOnClickListeners() {
    binding.rightArrow.setOnClickListener {
      navigateCluster(ClusterNavigateDirection.RIGHT, viewmodel.currentClusterName.value?.clusterID)
    }
    binding.leftArrow.setOnClickListener {
        navigateCluster(ClusterNavigateDirection.LEFT, viewmodel.currentClusterName.value?.clusterID)
    }

    binding.navBarButtonBinding.navBarButton.setOnClickListener() {
      findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToNavBarFragment())
    }

    binding.topBarBinding.Login.setOnClickListener() {
      findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToLoginFragment())
    }
  }
  private fun navigateCluster(direction: ClusterNavigateDirection, clusterID: Int?) {
    var index: Int? = null
    if (!viewmodel.clustersNames.value.isNullOrEmpty() && clusterID != null) {
      when (direction) {
        ClusterNavigateDirection.RIGHT -> {
          index = (clusterID + 1) % (viewmodel.clustersNames.value!!.size)
        }
        ClusterNavigateDirection.LEFT -> {
          index = (clusterID - 1) % (viewmodel.clustersNames.value!!.size)
          if (index < 0)
            index = viewmodel.clustersNames.value!!.size + index % (viewmodel.clustersNames.value!!.size)
        }
      }
    }
    if (index == 0) index = 12
    if (index != null) viewmodel.currentClusterName.postValue(ClusterName(getClusterName(index), index)) else viewmodel.currentClusterName.postValue(ClusterName("Exception", 0))
  }

  private fun getClusterID(enum: ClustersNameEnum): Int {
    return when (enum) {
      ClustersNameEnum.WorkshopGLInformal -> 0 // this route is not possible
      ClustersNameEnum.Dance -> 1
      ClustersNameEnum.Music -> 2
      ClustersNameEnum.Fashion -> 3
      ClustersNameEnum.English -> 4
      ClustersNameEnum.Gaming -> 5
      ClustersNameEnum.Dramatics -> 6
      ClustersNameEnum.Art -> 7
      ClustersNameEnum.Photography -> 8
      ClustersNameEnum.Shrutilaya -> 9
      ClustersNameEnum.Tamil -> 10
      ClustersNameEnum.Telugu -> 11
      ClustersNameEnum.Hindi -> 12
    }
  }

  private fun getClusterName(id: Int): String {
    return if (viewmodel.clustersNames.value != null) viewmodel.clustersNames.value!!.find { it.clusterID == id }?.name ?: "Null Exception" else "Cluster Name Not Available"
  }

  enum class ClusterNavigateDirection {
    LEFT, RIGHT
  }
}
