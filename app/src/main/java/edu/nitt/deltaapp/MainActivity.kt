package edu.nitt.deltaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import edu.nitt.deltaapp.core.BaseApplication
import edu.nitt.deltaapp.core.event.EventViewModel
import edu.nitt.deltaapp.databinding.ActivityMainBinding
import edu.nitt.deltaapp.helpers.viewLifecycle

class MainActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivityMainBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
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
}
