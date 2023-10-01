package edu.nitt.deltaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.event.EventViewModel
import edu.nitt.deltaapp.databinding.ActivityMainBinding
import edu.nitt.deltaapp.helpers.viewLifecycle

class MainActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivityMainBinding::inflate)
  private val postNotificationCode = 123

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    checkAndRequestPostNotificationsPermission()
  }

  override fun onStart() {
    super.onStart()

    val factory = (application as BaseApplication).applicationComponent.getViewModelProviderFactory()

    val eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)
    FirebaseMessaging.getInstance().subscribeToTopic(channelName)
    observeEventViewModel(eventViewModel)
  }

  private fun observeEventViewModel(eventViewModel: EventViewModel) {
    eventViewModel.success.observe(this,
      Observer {
//        showSnackbar_green("YAY!! $it")
      })

    eventViewModel.error.observe(this,
      Observer {
        showSnackbar_red("OOPS!! $it")
      })
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  private fun checkAndRequestPostNotificationsPermission() {
    val permission = Manifest.permission.POST_NOTIFICATIONS

    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
        showPermissionRationaleDialog()
      } else {
        ActivityCompat.requestPermissions(
          this,
          arrayOf(permission),
          postNotificationCode
        )
      }
    }
  }

  private fun showPermissionRationaleDialog() {
    AlertDialog.Builder(this)
      .setTitle("Permission Required")
      .setMessage("Please grant Notifications permission to receive notifications.")
      .setPositiveButton("OK") { _, _ ->
        ActivityCompat.requestPermissions(
          this,
          arrayOf(Manifest.permission.POST_NOTIFICATIONS),
          postNotificationCode
        )
      }
      .setNegativeButton("Cancel") { _, _ ->
        showSnackbar_red("Please grant Notifications permission to receive notifications.")
      }
      .show()
  }
}
