package edu.nitt.deltaapp.event

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.core.model.payload.Clusters.EventDetail
import edu.nitt.deltaapp.core.model.payload.Schedule.ScheduleData
import kotlinx.android.synthetic.main.schedule_list_item.view.colorChanging_BG
import kotlinx.android.synthetic.main.schedule_list_item.view.schedule_list_holder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ScheduleListRecyclerViewAdapter :
  ListAdapter<ScheduleData, ScheduleListRecyclerViewAdapter.EventHolder>(object : DiffUtil.ItemCallback<ScheduleData>() {
    override fun areItemsTheSame(oldItem: ScheduleData, newItem: ScheduleData): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: ScheduleData, newItem: ScheduleData):
      Boolean = (oldItem.name == newItem.name && oldItem.day == newItem.day && oldItem.time == newItem.time && oldItem.id == newItem.id)
  }) {
  val selectedItem = MutableLiveData<EventDetail?>()
  private var prevPosition = -1
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
    val inflater = LayoutInflater.from(parent.context)
    return EventHolder(inflater.inflate(R.layout.schedule_list_item, parent, false))
  }
  override fun onBindViewHolder(holder: EventHolder, position: Int) {
    holder.name.text = getItem(position).name
    holder.startTime.text = convertISOTo12HourTime(getItem(position).time)
    holder.venue.text = getItem(position).venue
    val en = holder.adapterPosition + 1
    holder.eventNumber.text = (en).toString()
    holder.view.setOnClickListener {
      val colorDrawables = arrayOf(ColorDrawable(), ColorDrawable(Color.BLACK))
      val transitionDrawable = TransitionDrawable(colorDrawables)
      it.colorChanging_BG.background = transitionDrawable
      transitionDrawable.startTransition(300)
      transitionDrawable.reverseTransition(300)
//      the below line is for making the list item clickable
//      selectedItem.value = getItem(position)
    }
    if (holder.adapterPosition > prevPosition) {
      holder.itemView.schedule_list_holder.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.schedule_list_item_enter_anim))
      prevPosition = holder.adapterPosition
    }
  }
  class EventHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.event_name_item)
    val startTime: TextView = view.findViewById(R.id.start_Time_item)
    val venue: TextView = view.findViewById(R.id.venue_item)
    val eventNumber: TextView = view.findViewById(R.id.event_number_id)
  }
  private fun convertISOTo12HourTime(isoDateString: String): String { // function for converting the given time format into 12 hours time
    if (!isoDateString.isNullOrEmpty()) {
      val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
      isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
      val date = isoFormatter.parse(isoDateString)
      val twelveHourFormatter = SimpleDateFormat("hh:mm a", Locale.US)
      twelveHourFormatter.timeZone = TimeZone.getDefault()
      return twelveHourFormatter.format(date as Date)
    } else {
      return ""
    }
  }
}
