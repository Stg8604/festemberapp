package edu.nitt.deltaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.models.ClusterDetails

class ClusterAdapter(
  context: Context,
  private val clusterList: List<ClusterDetails>,
  private val itemClickListener: (ClusterDetails) -> Unit
) : BaseAdapter() {

  private val inflater: LayoutInflater = LayoutInflater.from(context)
  interface OnItemClickListener {
    fun onItemClick(clusterDetails: ClusterDetails)
  }
  override fun getCount(): Int {
    return clusterList.size
  }
  override fun getItem(position: Int): Any {
    return clusterList[position]
  }
  override fun getItemId(position: Int): Long {
    return position.toLong()
  }
  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val itemView = convertView ?: inflater.inflate(R.layout.cluster_list_item, parent, false)
    val cluster = clusterList[position]
    val clusterImageView = itemView.findViewById<ImageView>(R.id.cluster_image)
    val clusterTextView = itemView.findViewById<TextView>(R.id.cluster_text)
    clusterImageView.setImageResource(cluster.imageResId)
    clusterTextView.text = cluster.name
    itemView.setOnClickListener {
      itemClickListener(cluster)
    }
    return itemView
  }
}
