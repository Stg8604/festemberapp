package edu.nitt.delta.adapters
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.delta.R
import edu.nitt.delta.models.AutoItem

class AutoAdapter(private val dataList: List<AutoItem>) :
  RecyclerView.Adapter<AutoAdapter.ViewHolder>() {
  private lateinit var mListener: onItemClickListener

  interface onItemClickListener {
    fun onItemClick(position: Int)
  }

  fun setOnItemClickListener(listener: onItemClickListener) {
    mListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.auto_item, parent, false)
    return ViewHolder(view, mListener)
  }
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentItem = dataList[position]
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    holder.autoDriverName.textSize = (screenWidth / 65).toFloat()
    holder.autoDriverNumber.textSize = (screenWidth / 65).toFloat()
    holder.dialerIcon.layoutParams.height = screenWidth / 13
    holder.avatarIcon.layoutParams.width = screenWidth / 9
    holder.autoDriverName.text = currentItem.autoDriverName
    holder.autoDriverNumber.text = currentItem.autoDriverNumber
    holder.dialerIcon.setImageResource(currentItem.dialer)
    holder.avatarIcon.setImageResource(currentItem.avatar)
  }
  override fun getItemCount(): Int {
    return dataList.size
  }
  class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
    val autoDriverName: TextView = itemView.findViewById(R.id.driverName)
    val autoDriverNumber: TextView = itemView.findViewById(R.id.driverNo)
    val dialerIcon: ImageView = itemView.findViewById(R.id.dialerIcon)
    val avatarIcon: ImageView = itemView.findViewById(R.id.avatarIcon)

    init {
      dialerIcon.setOnClickListener {
        listener.onItemClick(adapterPosition)
      }
    }
  }
}
