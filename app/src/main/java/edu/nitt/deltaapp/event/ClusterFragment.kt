package edu.nitt.deltaapp.event

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.adapters.ClusterAdapter
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.event.EventAction
import edu.nitt.deltaapp.core.event.EventViewModel
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import edu.nitt.deltaapp.databinding.FragmentClusterListBinding
import edu.nitt.deltaapp.fragments.LoginFragment
import edu.nitt.deltaapp.helpers.viewLifecycle
import edu.nitt.deltaapp.models.CarouselItemTypeEnum
import edu.nitt.deltaapp.models.ClusterDetails
import edu.nitt.deltaapp.models.ClustersNameEnum
import kotlinx.android.synthetic.main.fragment_cluster_list.gridView
import edu.nitt.deltaapp.showSnackbar_green
import kotlinx.android.synthetic.main.dashboard_fragment.view.topBarBinding
import kotlinx.android.synthetic.main.fragment_schedule.navBarButtonBinding
import kotlinx.android.synthetic.main.top_bar.view.Login
import kotlinx.android.synthetic.main.top_bar.view.Logout

private lateinit var viewmodel: EventViewModel
class ClusterFragment : Fragment(), ClusterAdapter.OnItemClickListener {
  private var binding by viewLifecycle<FragmentClusterListBinding>()
  private lateinit var sharedprefHelper: SharedPrefHelper
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

    val factory =
      (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory)[EventViewModel::class.java]

    val clusterEnumList = listOf(
      ClustersNameEnum.WorkshopGLInformal,
      ClustersNameEnum.Dance,
      ClustersNameEnum.Music,
      ClustersNameEnum.Fashion,
      ClustersNameEnum.English,
      ClustersNameEnum.Gaming,
      ClustersNameEnum.Dramatics,
      ClustersNameEnum.Art,
      ClustersNameEnum.Photography,
      ClustersNameEnum.Shrutilaya,
      ClustersNameEnum.Tamil,
      ClustersNameEnum.Telugu,
      ClustersNameEnum.Hindi
    )

    viewmodel.doAction(EventAction.GetClusters)
    viewmodel.clustersNames.removeObservers(viewLifecycleOwner)
    viewmodel.clustersNames.observe(viewLifecycleOwner) {
      gridView.adapter = ClusterAdapter(
        requireContext(),
        it.map { clusterName ->
          ClusterDetails(
            clusterEnumList[clusterName.clusterID],
            clusterName.name,
            getClusterResource(clusterEnumList[clusterName.clusterID])
          )
        }) { clusterItem -> onItemClick(clusterItem) }
    }

    navBarButtonBinding.setOnClickListener {
      sharedprefHelper =
        (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
      if (sharedprefHelper.isLoggedIn) {
        binding.topBarBinding.Logout.visibility = View.VISIBLE
        binding.topBarBinding.Login.visibility = View.INVISIBLE
      } else {
        binding.topBarBinding.Logout.Logout.visibility = View.INVISIBLE
        binding.topBarBinding.Logout.Login.visibility = View.VISIBLE
        binding.navBarButtonBinding.navBarButton.setOnClickListener {
          findNavController().navigate(ClusterFragmentDirections.actionEventsFragmentToNavBarFragment())
        }
        binding.topBarBinding.Logout.setOnClickListener {
          if (it.isVisible) {
            sharedprefHelper.isLoggedIn = false
            it.visibility = View.INVISIBLE
            binding.root.topBarBinding.Login.visibility = View.VISIBLE
            showSnackbar_green("Logged out Successfully")
          }
        }
        binding.topBarBinding.Login.setOnClickListener {
          if (it.isVisible) {
            findNavController().navigate(ClusterFragmentDirections.actionEventsFragmentToLoginFragment())
          }
        }
      }
    }
  }
  override fun onItemClick(clusterDetails: ClusterDetails) {
    findNavController().navigate(ClusterFragmentDirections.actionEventsFragmentToEventListFragment(clusterDetails.enumID, CarouselItemTypeEnum.Event))
  }

  private fun getClusterResource(enumID: ClustersNameEnum): Int {
    return when (enumID) {
      ClustersNameEnum.WorkshopGLInformal -> R.drawable.dancen // this route is not possible
      ClustersNameEnum.Dance -> R.drawable.dancen
      ClustersNameEnum.Music -> R.drawable.musict
      ClustersNameEnum.Fashion -> R.drawable.fashionn
      ClustersNameEnum.English -> R.drawable.eln
      ClustersNameEnum.Gaming -> R.drawable.gamingn
      ClustersNameEnum.Dramatics -> R.drawable.dramaticsn
      ClustersNameEnum.Art -> R.drawable.artn
      ClustersNameEnum.Photography -> R.drawable.phton
      ClustersNameEnum.Shrutilaya -> R.drawable.shrutin
      ClustersNameEnum.Tamil -> R.drawable.tln
      ClustersNameEnum.Telugu -> R.drawable.tlln
      ClustersNameEnum.Hindi -> R.drawable.hln
    }
  }
  private fun logout() {
    sharedprefHelper.clear()
    var intent = Intent(requireContext(), LoginFragment::class.java)
    startActivity(intent)
    requireActivity().finish()
  }
}
