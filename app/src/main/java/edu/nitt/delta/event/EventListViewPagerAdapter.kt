package edu.nitt.delta.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.nitt.delta.R
import edu.nitt.delta.core.PAYLOAD_BASE_URL
import edu.nitt.delta.core.model.payload.Clusters.EventDetail

class EventListViewPagerAdapter :
  ListAdapter<EventDetail, EventListViewPagerAdapter.EventHolder>(object :
    DiffUtil.ItemCallback<EventDetail>() {
    override fun areItemsTheSame(oldItem: EventDetail, newItem: EventDetail): Boolean =
      oldItem == newItem

    override fun areContentsTheSame(oldItem: EventDetail, newItem: EventDetail): Boolean =
      oldItem.name == newItem.name
  }) {

  val selectedItem = MutableLiveData<EventDetail?>()
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
    val inflater = LayoutInflater.from(parent.context)
    return EventHolder(inflater.inflate(R.layout.event_list_item, parent, false))
  }
  override fun onBindViewHolder(holder: EventHolder, position: Int) {
    holder.name.text = getItem(position).name
    holder.view.setOnClickListener {
      selectedItem.value = getItem(position)
    }
    Picasso.get().load(PAYLOAD_BASE_URL + getItem(position).image.url).fit().centerInside()
      .into(holder.poster)
  }

  class EventHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.event_name_vp)
    val poster: ImageView = view.findViewById(R.id.event_poster_vp)
  }
}
