package edu.nitt.delta.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.core.event.formatDateToDisplay
import edu.nitt.delta.core.event.formatTimeToDisplay
import edu.nitt.delta.core.model.event.EventData
import edu.nitt.delta.databinding.FragmentEventDetailBinding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar

class EventDetailsFragment : Fragment() {

  private var binder by viewLifecycle<FragmentEventDetailBinding>()
  private lateinit var viewmodel: EventViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binder = FragmentEventDetailBinding.inflate(inflater, container, false)
    return binder.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory).get(EventViewModel::class.java)
    val eventDetail = navArgs<EventDetailsFragmentArgs>().value.eventData

    initEventView(eventDetail)
    initOnClickListeners(eventDetail)
  }

  private fun initOnClickListeners(eventDetail: EventData) {
    binder.location.setOnClickListener {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse("google.navigation:q=${eventDetail.locationY},${eventDetail.locationX}")
      val chooser = Intent.createChooser(intent, "Choose app to show location")
      if (intent.resolveActivity(requireActivity().packageManager) != null) {
        startActivity(intent)
      } else {
        showSnackbar("Please install Google map")
      }
    }

    binder.btnSubscribe.setOnClickListener {
      viewmodel.doAction(EventAction.Subscribe(eventDetail.id))
    }
  }

  private fun initEventView(eventDetail: EventData) {
    binder.eventName.text = eventDetail.name
    binder.eventDescription.text = eventDetail.description
    binder.eventCluster.text = eventDetail.cluster
    binder.location.text = "x : ${eventDetail.locationX}, Y : ${eventDetail.locationY}"
    binder.eventDate.text = eventDetail.date.formatDateToDisplay()
    binder.eventTime.text = "${eventDetail.startTime.formatTimeToDisplay()} - ${eventDetail.endTime.formatTimeToDisplay()}"
    binder.eventVenue.text = eventDetail.venue
  }
}
