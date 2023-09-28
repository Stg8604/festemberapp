package edu.nitt.delta.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.model.user.ScoreboardData
import edu.nitt.delta.core.profile.ProfileAction
import edu.nitt.delta.core.profile.ProfileViewModel
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.databinding.FragmentLeaderboardBinding
import edu.nitt.delta.adapters.LeaderBoardAdapter
import edu.nitt.delta.models.LeaderBoardDetails
import edu.nitt.delta.helpers.viewLifecycle
import kotlinx.android.synthetic.main.fragment_schedule.navBarButtonBinding
import kotlin.collections.ArrayList
class LeaderBoardFragment : Fragment() {
  private var binding by viewLifecycle<FragmentLeaderboardBinding>()
  private lateinit var viewmodel: ProfileViewModel
  private var scoreDetails: ArrayList<LeaderBoardDetails> = ArrayList()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory).get(ProfileViewModel::class.java)
    viewmodel.doAction(ProfileAction.GetScoreboard)
    val scoresLiveData = viewmodel.scores
    scoresLiveData.observe(viewLifecycleOwner, Observer {
      initScores(it)
      navBarButtonBinding.setOnClickListener {
        findNavController().navigate(LeaderBoardFragmentDirections.actionLeaderboardfragmentToNavBarfragment())
      }
    })
  }
  private lateinit var sharedPrefHelper: SharedPrefHelper
  override fun onAttach(context: Context) {
    super.onAttach(context)
    sharedPrefHelper = (activity?.application as BaseApplication).applicationComponent.getSharedPrefManager()
  }
  private fun initScores(scores: List<ScoreboardData>) {
    scoreDetails.clear()
    for ((index, scoreData) in scores.withIndex()) {
      scoreDetails.add(
        LeaderBoardDetails(
          serialNumber = (index + 1).toString(),
          collegeName = scoreData.college_name,
          scoreField = scoreData.score
        )
      )
    }
    val recyclerView: RecyclerView = binding.recyclerView
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    recyclerView.adapter = LeaderBoardAdapter(scoreDetails)
  }
}
