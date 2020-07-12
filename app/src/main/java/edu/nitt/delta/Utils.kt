package edu.nitt.delta

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(
  msg: String,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  Snackbar.make(requireActivity().findViewById(android.R.id.content), msg, duration)
    .show()
}

fun Fragment.showSnackbar(
  msg: String,
  actionTitle: String,
  onClick: (View) -> Unit,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  Snackbar.make(requireActivity().findViewById(android.R.id.content), msg, duration)
    .setAction(actionTitle, onClick)
    .show()
}

fun AppCompatActivity.showSnackbar(
  msg: String,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  Snackbar.make(this.findViewById(android.R.id.content), msg, duration)
    .show()
}

fun AppCompatActivity.showSnackbar(
  msg: String,
  actionTitle: String,
  onClick: (View) -> Unit,
  duration: Int = Snackbar.LENGTH_SHORT
) {
  Snackbar.make(this.findViewById(android.R.id.content), msg, duration)
    .setAction(actionTitle, onClick)
    .show()
}
