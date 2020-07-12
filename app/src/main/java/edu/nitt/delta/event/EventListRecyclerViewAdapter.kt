package edu.nitt.delta.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.delta.R
import edu.nitt.delta.core.model.event.EventData

class EventListRecyclerViewAdapter :
  ListAdapter<EventData, EventListRecyclerViewAdapter.EventHolder>(object : DiffUtil.ItemCallback<EventData>() {
    override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean =
      oldItem == newItem

    override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean =
      oldItem == newItem
  }) {

  val selectedItem = MutableLiveData<EventData?>()
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
    val inflater = LayoutInflater.from(parent.context)
    return EventHolder(inflater.inflate(R.layout.event_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: EventHolder, position: Int) {
    holder.name.text = getItem(position).name
    holder.view.setOnClickListener {
      selectedItem.value = getItem(position)
    }
  }

  class EventHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.eventName)
  }
}
