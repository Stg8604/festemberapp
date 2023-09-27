package edu.nitt.delta.fragments
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.databinding.FragmentAutoBinding
import android.content.res.Resources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.nitt.delta.R
import edu.nitt.delta.adapters.AutoAdapter
import edu.nitt.delta.models.AutoItem
import edu.nitt.delta.showSnackbar

class AutoFragment : Fragment() {
  private var binding by viewLifecycle<FragmentAutoBinding>()
  private lateinit var autoRecyclerView: RecyclerView
  private lateinit var autoDriverList: MutableList<AutoItem>
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentAutoBinding.inflate(inflater, container, false)
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    binding.AutosHospitalityText.textSize = (screenWidth / 30).toFloat()
    binding.autoText.textSize = (screenWidth / 45).toFloat()
    binding.iconAutos.layoutParams.height = screenWidth / 13
    binding.iconAutos.layoutParams.width = screenWidth / 13
    autoDriverList = mutableListOf<AutoItem>()

    autoRecyclerView = binding.recyclerview
    autoRecyclerView.layoutManager = LinearLayoutManager(activity)
    autoRecyclerView.setHasFixedSize(true)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val autoDrivers = arrayOf(
      "driver1",
      "driver2",
      "driver3",
      "driver4",
      "driver5",
      "driver6",
      "driver7",
      "driver8",
      "driver9",
      "driver10"
    )
    val autoDriverNumbers = arrayOf(
      "9999999990",
      "9999999991",
      "9999999992",
      "9999999993",
      "9999999994",
      "9999999995",
      "9999999996",
      "9999999997",
      "9999999998",
      "9999999999"
    )
    // replace with any other preferred array of data-if any
    val avatarList = arrayOf(
      R.drawable.avatar1,
      R.drawable.avatar2,
      R.drawable.avatar3,
      R.drawable.avatar4,
      R.drawable.avatar5
    )
    val autoDriverTotalNo = 10
    for (i in 0..autoDriverTotalNo - 1) {
      val autoDriverElement =
        AutoItem(autoDrivers[i], autoDriverNumbers[i], R.drawable.auto_dialer, avatarList[i % 5])
      autoDriverList.add(autoDriverElement)
    }
    val adapter = AutoAdapter(autoDriverList)
    autoRecyclerView.adapter = adapter
    adapter.setOnItemClickListener(listener = object : AutoAdapter.onItemClickListener {
      override fun onItemClick(position: Int) {
        val phoneNumber = autoDriverNumbers[position]
        val dialIntent = Intent(Intent.ACTION_DIAL)
        if (phoneNumber.isNotEmpty()) {
          dialIntent.data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(dialIntent)
        showSnackbar("dialing  , ${autoDrivers[position]}")
      }
    })
  }
}
