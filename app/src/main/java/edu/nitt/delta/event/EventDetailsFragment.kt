package edu.nitt.delta.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.text.HtmlCompat.fromHtml
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.PAYLOAD_BASE_URL
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.core.model.payload.Clusters.EventDetail
import edu.nitt.delta.databinding.FragmentEventDetailBinding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.models.CarouselItemTypeEnum
import java.text.SimpleDateFormat
import java.util.Date

class EventDetailsFragment : Fragment() {

  private var binder by viewLifecycle<FragmentEventDetailBinding>()
  private lateinit var viewmodel: EventViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binder = FragmentEventDetailBinding.inflate(inflater, container, false)
    return binder.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val factory = (requireActivity().application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    viewmodel = ViewModelProvider(requireActivity(), factory).get(EventViewModel::class.java)
    val eventDetail = navArgs<EventDetailsFragmentArgs>().value.eventDetail
    val itemType = navArgs<EventDetailsFragmentArgs>().value.itemType

    if (itemType != CarouselItemTypeEnum.Event) {
      binder.prizesHeading.visibility = View.GONE
      binder.prizesDescription.visibility = View.GONE
      binder.judgingCriteriaHeading.visibility = View.GONE
      binder.judgingCriteriaDescription.visibility = View.GONE
    }

    initEventView(eventDetail)
    initOnClickListeners(eventDetail)
  }

  private fun initOnClickListeners(eventDetail: EventDetail) {
    if (!eventDetail.registrationLink.isNullOrBlank())
      binder.materialButton.setOnClickListener {
        Intent(Intent.ACTION_VIEW).let {
          it.data = Uri.parse(if (eventDetail.registrationLink.contains("https://")) eventDetail.registrationLink else "https://" + eventDetail.registrationLink)
          startActivity(it)
        }
      }
    else binder.materialButton.setOnClickListener {
      Toast.makeText(context, "Registration Link not available.", Toast.LENGTH_SHORT).show()
    }
    binder.navBarButtonBinding.navBarButton.setOnClickListener() {
      findNavController().navigate(EventDetailsFragmentDirections.actionEventDetailsFragmentToNavBarFragment())
    }
    binder.topBarBinding.Login.setOnClickListener() {
      findNavController().navigate(EventDetailsFragmentDirections.actionEventDetailsFragmentToLoginFragment())
    }
  }

  private fun initEventView(eventDetail: EventDetail) {
    val eventTime: Date? = if (!eventDetail.time.isNullOrBlank()) SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(eventDetail.time) else null
    val timeFormatter = SimpleDateFormat("hh:mm a")
    val dateFormatter = SimpleDateFormat("EE, d MMMM")

    binder.eventName.text = eventDetail.name
    binder.eventDescription.text = if (!eventDetail.description.isNullOrBlank()) eventDetail.description else "Event Description is not available right now."
    // binder.eventDate.text = if (eventTime != null) fromHtml("<b> Date: </b> ${dateFormatter.format(eventTime)}", FROM_HTML_MODE_LEGACY) else "Date: Not available."
    binder.eventTime.text = if (eventTime != null) fromHtml("<b> Time: </b> ${timeFormatter.format(eventTime)}", FROM_HTML_MODE_LEGACY) else "Time: Not available."
    binder.eventDate.text = if (eventDetail.day != null) "Day: " + eventDetail.day else "Event Day is not available right now."
    binder.eventVenue.text = if (!eventDetail.venue.isNullOrBlank()) eventDetail.venue else "Event Venue is not available right now."
    binder.judgingCriteriaDescription.text = if (!eventDetail.htmlJudgingCriteria.isNullOrBlank()) fromHtml(eventDetail.htmlJudgingCriteria, FROM_HTML_MODE_LEGACY) else "Judging Crtieria is not available right now."
    binder.prizesDescription.text = if (!eventDetail.prize.isNullOrEmpty()) eventDetail.prize.joinToString(separator = "\n") { it -> it.prizeName + ":" + " ${it.prizeAmount}" } else "Detail on prizes is not available right now."
    binder.pocDescription.text = if (!eventDetail.contact.isNullOrEmpty()) eventDetail.contact.joinToString(separator = "\n") { it -> it.contactName + ":" + " ${it.contactNumber}" } else "Contact information is not available right now."
    binder.eventPosterView.contentDescription = if (!eventDetail.image.alt.isNullOrBlank()) eventDetail.image.alt else "Content Description is not available"
    Picasso.get().load(PAYLOAD_BASE_URL + eventDetail.image.url).resize(250, 250).centerInside().into(binder.eventPosterView)
  }
}
