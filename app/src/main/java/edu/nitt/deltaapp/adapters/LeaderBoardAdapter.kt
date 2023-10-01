package edu.nitt.deltaapp.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.deltaapp.R

import edu.nitt.deltaapp.models.LeaderBoardDetails
class LeaderBoardAdapter(private val dataList: List<LeaderBoardDetails>) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val serialNumberTextView: TextView = itemView.findViewById(R.id.textView1)
    val collegeNameTextView: TextView = itemView.findViewById(R.id.textView2)
    val scoreFieldTextView: TextView = itemView.findViewById(R.id.textView3)
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_item, parent, false)
    return ViewHolder(view)
  }
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentItem = dataList[position]
    holder.serialNumberTextView.text = currentItem.serialNumber
    holder.collegeNameTextView.text = currentItem.collegeName
    holder.scoreFieldTextView.text = currentItem.scoreField
  }
  override fun getItemCount(): Int {
    return dataList.size
  }
}
