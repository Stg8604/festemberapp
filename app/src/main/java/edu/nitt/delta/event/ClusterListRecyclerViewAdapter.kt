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

class ClusterListRecyclerViewAdapter :
  ListAdapter<String, ClusterListRecyclerViewAdapter.ClusterHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
      oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
      oldItem == newItem
  }) {

  val selectedItem = MutableLiveData<String>()
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClusterHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ClusterHolder(inflater.inflate(R.layout.cluster_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: ClusterHolder, position: Int) {
    holder.name.text = getItem(position)
    holder.view.setOnClickListener {
      selectedItem.value = getItem(position)
    }
  }

  class ClusterHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById<TextView>(R.id.event_name_item)
  }
}
