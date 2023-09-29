package edu.nitt.delta

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import kotlinx.android.synthetic.main.snackbar_red.view.snack_message
fun Fragment.showSnackbar_green(
  msg: String,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  val snackBar: Snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "", duration)
  val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_green, null)
  snackBar.view.setBackgroundColor(Color.TRANSPARENT)
  val snackBarLayout = snackBar.view as SnackbarLayout
  snackBarLayout.setPadding(0, 0, 0, 0)
  snackBarLayout.addView(customSnackView, 0)
  snackBarLayout.snack_message.text = msg
  snackBar.show()
}

fun Fragment.showSnackbar_red(
  msg: String,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  val snackBar: Snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "", duration)
  val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_red, null)
  snackBar.view.setBackgroundColor(Color.TRANSPARENT)
  val snackBarLayout = snackBar.view as SnackbarLayout
  snackBarLayout.setPadding(0, 0, 0, 0)
  snackBarLayout.addView(customSnackView, 0)
  snackBarLayout.snack_message.text = msg
  snackBar.show()
}
fun AppCompatActivity.showSnackbar_green(
  msg: String,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  val snackBar: Snackbar = Snackbar.make(this.findViewById(android.R.id.content), "", duration)
  val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_green, null)
  snackBar.view.setBackgroundColor(Color.TRANSPARENT)
  val snackBarLayout = snackBar.view as SnackbarLayout
  snackBarLayout.setPadding(0, 0, 0, 0)
  snackBarLayout.addView(customSnackView, 0)
  snackBarLayout.snack_message.text = msg
  snackBar.show()
}
fun AppCompatActivity.showSnackbar_red(
  msg: String,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  val snackBar: Snackbar = Snackbar.make(this.findViewById(android.R.id.content), "", duration)
  val customSnackView: View = layoutInflater.inflate(R.layout.snackbar_red, null)
  snackBar.view.setBackgroundColor(Color.TRANSPARENT)
  val snackBarLayout = snackBar.view as SnackbarLayout
  snackBarLayout.setPadding(0, 0, 0, 0)
  snackBarLayout.addView(customSnackView, 0)
  snackBarLayout.snack_message.text = msg
  snackBar.show()
}
