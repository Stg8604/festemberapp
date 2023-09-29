package edu.nitt.delta.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import edu.nitt.delta.R
import edu.nitt.delta.core.PAYLOAD_BASE_URL
import edu.nitt.delta.core.model.payload.Sponsors.SponsorsData
class SponsorsAdapter(context: Context, sponsors: List<SponsorsData>) : BaseAdapter() {
  private var sponsorList: List<SponsorsData> = sponsors
  private var layoutInflater: LayoutInflater? = null
  init {
    this.sponsorList = sponsors
    layoutInflater = LayoutInflater.from(context)
  }

  override fun getCount(): Int {
    return sponsorList.size
  }

  override fun getItem(position: Int): SponsorsData {
    return sponsorList[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View? = layoutInflater?.inflate(R.layout.sponsors_list_item, parent, false)
    val sponsorImage = view?.findViewById<ImageView>(R.id.imageSponsor)
    val portfolio = view?.findViewById<TextView>(R.id.portfolio)
    Picasso.get().load(PAYLOAD_BASE_URL + sponsorList[position].image.url).into(sponsorImage)
    portfolio!!.text = sponsorList[position].sponsorname
    return view
  }
}
